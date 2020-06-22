package com.codenation.curso.central.error.log.test;

import static org.junit.Assert.assertEquals;
import java.time.LocalDate;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.codenation.curso.central.error.CentralErrosApplication;
import com.codenation.curso.central.error.dto.request.LogRequest;
import com.codenation.curso.central.error.models.ErrorLevelsEnum;
import com.codenation.curso.central.error.models.Log;
import com.codenation.curso.central.error.repositories.LogRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=CentralErrosApplication.class)

public class LogSingleTest {
	private ModelMapper modelMapper = new ModelMapper();
	@Autowired
	private LogRepository repository;
	

	
 public LogSingleTest(){
	
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
	public void createShouldPersistData()
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
    
    @Test
   	public void getShouldGetAllData() {
    	
       	Log log = new Log(1L,ErrorLevelsEnum.ERROR,"teste","teste",LocalDate.of(2020, 2, 10),3);
       	Log log2 = new Log(1L,ErrorLevelsEnum.ERROR,"teste","teste",LocalDate.of(2020, 2, 10),3);
       	this.repository.save(log);
   		this.repository.save(log2);
   	   List<Log> logs = repository.findAll();
   	   
   	   Assertions.assertThat(logs.size()).isEqualTo(2);
   		
   	}
    
    
    
    
    
    
    
}
