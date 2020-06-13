package com.mballen.curso.boot;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.codenation.curso.central.error.dto.request.LogRequest;
import com.codenation.curso.central.error.models.ErrorLevelsEnum;
import com.codenation.curso.central.error.models.Log;
import com.codenation.curso.central.error.models.User;
import com.codenation.curso.central.error.repositories.LogRepository;
import com.codenation.curso.central.error.service.interfaces.LogService;
@RunWith(SpringRunner.class)
@DataJpaTest
public class LogTest {
	private ModelMapper modelMapper = new ModelMapper();
	@Autowired
	private LogRepository repository;
	
 public LogTest(){
	
}
    @Test
    public void whenConvertPostEntityToPostDto_thenCorrect() {
        Log log = new Log();
        ErrorLevelsEnum level = ErrorLevelsEnum.ERROR;

        log.setId(1L);
        log.setErrorLevel(level);
        log.setDescription("teste");

        LogRequest logDtoRequest = modelMapper.map(log, LogRequest.class);
        assertEquals(log.getErrorLevel(), logDtoRequest.getErrorLevel());
        assertEquals(log.getDescription(), logDtoRequest.getDescription());
        assertEquals(log.getDate(), logDtoRequest.getDate());
        assertEquals(log.getOrigin(), logDtoRequest.getOrigin());
        assertEquals(log.getQuantity(), logDtoRequest.getQuantity());
        
        
    }
    @Test
	public void contextLoadscreateShouldPersistData()
    {
		Log log = new Log(1L,ErrorLevelsEnum.ERROR,"teste","teste",LocalDate.of(2020, 2, 10),3);
	
		this.repository.save(log);
		Assertions.assertThat(log.getDate()).isEqualTo(LocalDate.of(2020, 2, 10));
		Assertions.assertThat(log.getDescription()).isEqualTo("teste");
		Assertions.assertThat(log.getOrigin()).isEqualTo("teste");
		Assertions.assertThat(log.getQuantity()).isEqualTo(3);
		
	}
    @Test
	public void deleteShouldRemoveData() {
    	Log log = new Log(1L,ErrorLevelsEnum.ERROR,"teste","teste",LocalDate.of(2020, 2, 10),3);
		this.repository.save(log);
		this.repository.delete(log);
		Assertions.assertThat(this.repository.findById(log.getId())).isEmpty();
	}
    @Test
	public void updateShouldChangeAndPersistData() {
    	Log log = new Log(1L,ErrorLevelsEnum.ERROR,"teste","teste",LocalDate.of(2020, 2, 10),3);
		this.repository.save(log);
		log.setErrorLevel(ErrorLevelsEnum.WARNING);
		log.setDate(LocalDate.of(2020, 2, 15));
		log.setQuantity(4);
		this.repository.save(log);
		Assertions.assertThat(log.getErrorLevel()).isEqualTo(ErrorLevelsEnum.WARNING);
		Assertions.assertThat(log.getDate()).isEqualTo(LocalDate.of(2020, 2, 15));
		Assertions.assertThat(log.getQuantity()).isEqualTo(4);
		
		
	}
}
