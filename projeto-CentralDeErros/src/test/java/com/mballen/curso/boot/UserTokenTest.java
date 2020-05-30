package com.mballen.curso.boot;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.mballen.curso.boot.model.User;
import com.mballen.curso.boot.repositories.UserRepository;
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserTokenTest {
	@Autowired
	private TestRestTemplate restTemplate;
	@LocalServerPort
	private int port;
	@MockBean
	private UserRepository repository;
	@Autowired
	private MockMvc mockMvc;
	private HttpEntity<Void> protectedHeader;
	//private HttpEntity<Void> adminHeader;
	private HttpEntity<Void> wrongHeader;
	
    @Autowired
    private WebApplicationContext context;
	
	
	@Before
	public void configProtectedHeaders() {
		//usuario correto
		String str = "{\r\n" + 
				"	\"userEmail\":\"day@day\",\r\n" + 
				"	\"password\":\"123\"\r\n" + 
				"}";
		HttpHeaders headers = restTemplate.postForEntity("/login", str, String.class).getHeaders();
		this.protectedHeader = new HttpEntity<>(headers);
		//recebe o token para fazer as requisições
	}
	
	//@Before
	public void configWrongHeaders() {
		String str = "{\r\n" + 
				"	\"userEmail\":\"day@dayy\",\r\n" + 
				"	\"password\":\"12345\"\r\n" + 
				"}";
		HttpHeaders headers = restTemplate.postForEntity("/login", str, String.class).getHeaders();
				this.wrongHeader = new HttpEntity<>(headers);
	}
	
	


	@Test
	public void listUserWhenTokenIsCorrectShouldReturnStatusCode200() {
	
		ResponseEntity<User> response= restTemplate.exchange("/user/1/",HttpMethod.GET,protectedHeader, User.class);
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
	}
	@Test
	public void listUserWhenTokenIsIncorrectShouldReturnStatusCode401() {
		String str = "{\r\n" + 
				"	\"userEmail\":\"day@dayy\",\r\n" + 
				"	\"password\":\"12345\"\r\n" + 
				"}";
		HttpHeaders headers = restTemplate.postForEntity("/login", str, String.class).getHeaders();
				this.wrongHeader = new HttpEntity<>(headers);
		ResponseEntity<User> response= restTemplate.exchange("/user/1",HttpMethod.GET,wrongHeader, User.class);
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(401);
	}

}
