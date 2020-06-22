package com.codenation.curso.central.error.user.test;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.nio.charset.Charset;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.codenation.curso.central.error.repositories.UserRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserEndPointTest {

@LocalServerPort
private int port;
@MockBean
private UserRepository userRepository;
@Autowired
private MockMvc mockmvc;


String token = "BearereyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJEQVlAMTIzNC5DT00iLCJleHAiOjE1OTIzNTkzNTR9.5ksunfGR3i798nnS3a_g_C7Yg2WMYVp36cBrtiThqYpHfKJqnNeOit-EUDYI8ufQaME8yZp7rjxasK2vJhmN5Q";
private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
	      MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
Gson gson = new GsonBuilder().create();

//-------------- sem token----------
/*
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
*/

// --------com token ----------

@Test
public void shouldReturnAccessDenied() throws Exception {
	
	mockmvc.perform(MockMvcRequestBuilders.get("/user/1")).andExpect(status().isForbidden());

  
}

@Test
public void shouldReturn200FindById() throws Exception {
  

	mockmvc.perform(MockMvcRequestBuilders.get("/user/1")
            .header("Authorization", token))
            .andExpect(status().isOk());
}

@Test
public void shouldReturn200DeleteById() throws Exception {
  
	mockmvc.perform(MockMvcRequestBuilders.delete("/user/1")
            .header("Authorization", token))
            .andExpect(status().isOk());
}


@Test
public void shouldReturn201SaveUser() throws Exception {

	String eventJosn = "{\n" + 
			"  \"name\": \"Daiana\",\n" + 
			"  \"password\": \"123\",\n" + 
			"  \"userEmail\": \"DAY@DAY\"\n" + 
			"}";
	
	
	MvcResult result= mockmvc.perform(MockMvcRequestBuilders.post("/user")
            .header("Authorization", token) .contentType(APPLICATION_JSON_UTF8).content(eventJosn)).andReturn();
    int status = result.getResponse().getStatus();
    assertEquals(201, status);
}

@Test
public void shouldReturn200UpdateUser() throws Exception {

	String eventJosn = " {\n" + 
			"  \"name\": \"Daiana\",\n" + 
			"  \"password\": \"123\",\n" + 
			"  \"userEmail\": \"DAY@DAYANE\"\n" + 
			"}";
	
	
	MvcResult result= mockmvc.perform(MockMvcRequestBuilders.put("/user/2")
            .header("Authorization", token) .contentType(APPLICATION_JSON_UTF8).content(eventJosn)).andReturn();
    int status = result.getResponse().getStatus();
    assertEquals(200, status);
}

@Test
public void shouldReturn200GetAll() throws Exception {
	
	mockmvc.perform(MockMvcRequestBuilders.get("/user/users")
            .header("Authorization", token))
            .andExpect(status().isAccepted());
}





}
