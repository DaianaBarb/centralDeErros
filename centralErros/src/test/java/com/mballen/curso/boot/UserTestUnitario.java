package com.mballen.curso.boot;
import static org.junit.Assert.assertEquals;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.codenation.curso.central.error.dto.response.UserResponse;
import com.codenation.curso.central.error.models.User;
import com.codenation.curso.central.error.repositories.UserRepository;
//como faz para utilizarmos nosso proprio banco de dados para fazer os testes?
//e so colocar a anotação:
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@SpringBootTest
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserTestUnitario {
	

		
public  UserTestUnitario() {
	
	}
private ModelMapper modelMapper = new ModelMapper();
@Autowired
private UserRepository userRepository;
@Rule
public ExpectedException thrown = ExpectedException.none();

	@Test
	public void contextLoadscreateShouldPersistData() {
		User user= new User((long)1,"user@user","user","123","ADMIN",(short)1);
		this.userRepository.save(user);
		Assertions.assertThat(user.getId()).isNotNull();
		Assertions.assertThat(user.getName()).isEqualTo("user");
		Assertions.assertThat(user.getUserEmail()).isEqualTo("user@user");
		Assertions.assertThat(user.getPassword()).isEqualTo("123");
		Assertions.assertThat(user.getRole()).isEqualTo("ADMIN");
		Assertions.assertThat(user.getEnabled()).isEqualTo((short)1);
	}
	@Test
	public void deleteShouldRemoveData() {
		User user= new User((long)1,"user","user@user.com","123","ADMIN",(short)1);
		this.userRepository.save(user);
		this.userRepository.delete(user);
		Assertions.assertThat(this.userRepository.findById(user.getId())).isEmpty();
	}
	@Test
	public void updateShouldChangeAndPersistData() {
		User user= new User((long)1,"user","user@user.com","123","ADMIN",(short)1);
		this.userRepository.save(user);
		user.setName("Daiana");
		user.setUserEmail("Daiana@Daiana.com");
		this.userRepository.save(user);
		Assertions.assertThat(user.getName()).isEqualTo("Daiana");
		Assertions.assertThat(user.getUserEmail()).isEqualTo("Daiana@Daiana.com");
		
		
	}

	 @Test
	    public void whenConvertPostEntityToPostDto_thenCorrect() {
		 User user = new  User();
	        user.setId(2L);
	        user.setName("daiana");
	        user.setUserEmail("day@day");
	        
	        UserResponse userDtoRequest = modelMapper.map(user, UserResponse.class);
	        assertEquals(user.getId(), userDtoRequest.getId());
	        assertEquals(user.getName(), userDtoRequest.getName());
	        assertEquals(user.getUserEmail(), userDtoRequest.getUserEmail());
	 }
	 
	 @Test
	    public void whenConvertPostDtoToPostEntity_thenCorrect() {
		 
	        UserResponse userDtoRequest = new  UserResponse();
	        userDtoRequest.setId(2L);
	        userDtoRequest.setName("daiana");
	        userDtoRequest.setUserEmail("day@day");

	        User user = modelMapper.map(userDtoRequest, User.class);
	        assertEquals(userDtoRequest.getId(), user.getId());
	        assertEquals(userDtoRequest.getName(), user.getName());
	        assertEquals(userDtoRequest.getUserEmail(), user.getUserEmail());
	    }
	 
	 @Test
	   	public void getShouldGetAllData() {
	    	
		 User user= new User((long)1,"user","user@user.com","123","ADMIN",(short)1);
		 User user1= new User((long)1,"user","user@user.com","123","ADMIN",(short)1);
	       	this.userRepository.save(user);
	   		this.userRepository.save(user1);
	   	   List<User> users = userRepository.findAll();
	   	   
	   	   Assertions.assertThat(users.size()).isEqualTo(2);
	   		
	   	}
	    
	 
	 
}
