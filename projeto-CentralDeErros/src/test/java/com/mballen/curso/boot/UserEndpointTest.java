package com.mballen.curso.boot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.mballen.curso.boot.model.User;
import com.mballen.curso.boot.repositories.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserEndpointTest {
@Autowired
private TestRestTemplate restTemplate;
@LocalServerPort
private int port;
@MockBean
private UserRepository userRepository;
@Autowired
private MockMvc mockmvc;



@TestConfiguration
static class Config{
	public RestTemplateBuilder restTemplateBuilder() {
		return new RestTemplateBuilder().basicAuthorization("day@day","123");
	}
}

@Test
public void listUserWhenUserNameAndPasswordAreIncorrectShouldReturnStatusCode204 () {
	restTemplate = restTemplate.withBasicAuth("1", "1");
	ResponseEntity<String> response= restTemplate.getForEntity("/user/users", String.class);
	Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(204);
}

@Test
public void getUserByIdAreIncorrectShouldReturnStatusCode500() {
	System.out.println(port);
	restTemplate = restTemplate.withBasicAuth("1", "1");
	ResponseEntity<String> response= restTemplate.getForEntity("/user/2",String.class);
	Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(500);
}

@Test
public void listUserWhenUsernameAndPasswordAreCorrectShouldReturnStatusCode200() {
	User user= new User((long)1,"user@user","user","123","ADMIN",(short)1);
	User user2
	= new User((long)1,"user@user","user","123","ADMIN",(short)1);
	List<User>lista = new ArrayList<>();
	lista.add(user);
	lista.add(user2);
	BDDMockito.when(userRepository.findAll()).thenReturn(lista);
	ResponseEntity<String> response= restTemplate.getForEntity("/user/users",String.class);
	Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(204);
}

@Test
public void buscarPorId() {
	Optional<User> user= Optional.ofNullable(new User((long)1,"user@user","user","123","ADMIN",(short)1));
	
	BDDMockito.when(userRepository.findById(user.get().getId())).thenReturn(user);
	ResponseEntity<String> response= restTemplate.getForEntity("/user/{id}",String.class,user.get().getId());
	Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(202);
	
}
@Test
public void naoEncontrado() {
	ResponseEntity<String> response= restTemplate.getForEntity("/user/{id}",String.class,30);
	Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(404);
}



















}
