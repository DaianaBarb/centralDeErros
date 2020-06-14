package com.mballen.curso.boot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.codenation.curso.central.error.dto.response.UserResponse;
import com.codenation.curso.central.error.models.User;
import com.codenation.curso.central.error.repositories.UserRepository;

import javassist.NotFoundException;

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

@Autowired
private ModelMapper modelMapper;


@Test
public void searchUsersShouldReturnCode200() {
	Optional<User> user= Optional.ofNullable(new User((long)1,"user@user","user","123","ADMIN",(short)1));
	Optional<User> user2= Optional.ofNullable(new User((long)2,"user@user2","user","123","ADMIN",(short)1));
	List<User> users = new ArrayList<>();
	users.add(user.get());
	users.add(user2.get());
	BDDMockito.when(userRepository.findAll()).thenReturn(users);
	ResponseEntity<String> response= restTemplate.getForEntity("/user/{id}",String.class,user.get().getId());
	Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
	
}
@Test
public void notfoundUserShouldReturnCode200() {
	Optional<User> user= Optional.ofNullable(new User((long)1,"user@user","user","123","ADMIN",(short)1));
	BDDMockito.when(userRepository.findById(user.get().getId())).thenReturn( user);
	ResponseEntity<String> response= restTemplate.getForEntity("/user/{id}",String.class, user.get().getId());
	Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
}









/*
 

 * ---------------------------------------------------
 @Test
public void listUserWhenUserNameAndPasswordAreIncorrectShouldReturnStatusCode200 () {
	//restTemplate = restTemplate.withBasicAuth("1", "1");
	ResponseEntity<String> response= restTemplate.getForEntity("/user/users", String.class);
	Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
}

@Test
public void getUserByIdAreIncorrectShouldReturnStatusCode500() {
//	System.out.println(port);
	//restTemplate = restTemplate.withBasicAuth("1", "1");
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
	ResponseEntity<String> response= restTemplate.getForEntity("/user/users/",String.class);
	Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
}

@Test
public void buscarPorId() {
	Optional<User> user= Optional.ofNullable(new User((long)1,"user@user","user","123","ADMIN",(short)1));
	
	BDDMockito.when(userRepository.findById(user.get().getId())).thenReturn(user);
	ResponseEntity<String> response= restTemplate.getForEntity("/user/{id}",String.class,user.get().getId());
	Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
	
}
@Test
public void naoEncontrado() {
	ResponseEntity<String> response= restTemplate.getForEntity("/user/{id}",String.class,30);
	Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(404);
}
 
 
 */



}
