package com.mballen.curso.boot;
import static org.junit.Assert.assertEquals;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.codenation.curso.central.error.dto.request.*;
import com.codenation.curso.central.error.dto.response.LogResponse;
import com.codenation.curso.central.error.models.ErrorLevelsEnum;
import com.codenation.curso.central.error.models.Log;
import com.codenation.curso.central.error.models.User;
import com.codenation.curso.central.error.repositories.UserRepository;
//como que faz para utilizarmos nosso proprio banco de dados para fazer os testes?
//e so colocar a anotação:
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@SpringBootTest
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserTest {
	

		
public  UserTest() {
	
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
		 Log log = new Log();
	        ErrorLevelsEnum level = ErrorLevelsEnum.ERROR;

	        log.setId(1L);
	        log.setErrorLevel(level);
	        log.setDescription("teste");
	        LogResponse logDtoRequest = modelMapper.map(log, LogResponse.class);
	        assertEquals(log.getId(), logDtoRequest.getId());
	        assertEquals(log.getErrorLevel(), logDtoRequest.getErrorLevel());
	        assertEquals(log.getDescription(), logDtoRequest.getDescription());
	 }
	 
	 @Test
	    public void whenConvertPostDtoToPostEntity_thenCorrect() {
	        LogResponse logDtoRequest = new LogResponse();
	        ErrorLevelsEnum level = ErrorLevelsEnum.INFO;

	        logDtoRequest.setId(2L);
	        logDtoRequest.setErrorLevel(level);
	        logDtoRequest.setDescription("test");

	        Log log = modelMapper.map(logDtoRequest, Log.class);
	        assertEquals(logDtoRequest.getId(), log.getId());
	        assertEquals(logDtoRequest.getErrorLevel(), log.getErrorLevel());
	        assertEquals(logDtoRequest.getDescription(), log.getDescription());
	    }
	 
	 
}
