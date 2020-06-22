package com.codenation.curso.central.error.services;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.codenation.curso.central.error.dto.request.LogRequest;
import com.codenation.curso.central.error.dto.response.LogResponse;
import com.codenation.curso.central.error.models.Log;
import com.codenation.curso.central.error.repositories.LogRepository;
import com.codenation.curso.central.error.service.interfaces.LogService;
import com.querydsl.core.types.Predicate;

@Service
public class LogServiceImple implements LogService {
	
	@Autowired
    private LogRepository logRepository;
	
    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<LogResponse> save(LogRequest log) {
    	
    	 Log logCreated = logRepository.save(log.transformaParaObjeto());
         
	        return new ResponseEntity<LogResponse> (convertToDto(logCreated),HttpStatus.CREATED);
      
   
    }
    public Page<Log> getLogsList(int page, int size){
            Page<Log> pageResult = null;
       
            Pageable paging = PageRequest.of(page, size);
            pageResult = logRepository.findAll(paging);
             return pageResult;
    }
    
    public ResponseEntity <List<LogResponse>> sortLogs(String param, int page, int size) {
    	
    	
            Page<Log> pageResult = null;
        
            Pageable paging = PageRequest.of(page, size, Sort.by(param).ascending());
            pageResult = logRepository.findAll(paging);
            
           
	        Page<LogResponse> pageLog = new PageImpl<>(pageResult.getContent().stream()
	                .map(this::convertToDto)
	                .collect(Collectors.toList()));

	        return new ResponseEntity <List<LogResponse>>(pageLog.getContent(),HttpStatus.ACCEPTED);
           
    }
    
	@Override
	public ResponseEntity<Log> findById(Long id) {
		Optional<Log> log = logRepository.findById(id);
		   if(log.isPresent()) {
			   return new  ResponseEntity<Log>(log.get(),HttpStatus.OK);
		   }
		   
		   return new ResponseEntity<Log>(HttpStatus.NOT_FOUND);

	}
	
	@Override
	public ResponseEntity<List<LogResponse>> finfAllLogs(int page, int size) {
		
		Pageable paging = PageRequest.of(page, size);
		Page<Log>  logs = logRepository.findAll(paging);
        Page<LogResponse> pageLog = new PageImpl<>(logs.getContent()
        		.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));

        return new ResponseEntity <List<LogResponse>>(pageLog.getContent(),HttpStatus.ACCEPTED);
		
		
	}
	@Override
	public ResponseEntity<Void> deleteLog(Long id) {
		
		
        Optional<Log> log = this.logRepository.findById(id);

        if (log.isPresent()) {
        	logRepository.deleteById(id);
        	
            return new ResponseEntity<Void>(HttpStatus.OK);
        } 
        
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"); 
		
	}
	@Override
	public ResponseEntity<List<LogResponse>> findAllPredicate(Predicate predicate, int page, int size) {
		Pageable paging = PageRequest.of(page, size);
		
        Page<Log> logs = logRepository.findAll(predicate, paging);
    
    	
    	Page<LogResponse> pageLog = new PageImpl<>(logs.getContent().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));

        return new ResponseEntity <List<LogResponse>>(pageLog.getContent(),HttpStatus.ACCEPTED);
      
    }
	@Override
	public ResponseEntity<List<LogResponse>> saveAll(List<LogRequest> logs) {
		
		   List< Log> logsCreated = logRepository.saveAll(logs.stream().map(log->log
				.transformaParaObjeto())
				.collect(Collectors.toList()));
	 
	  return new ResponseEntity<List<LogResponse>>(  logsCreated.stream().map(this::convertToDto).collect(Collectors.toList()),HttpStatus.CREATED);
	}
	
	
	
	 private LogResponse convertToDto(Log log) { return modelMapper.map(log, LogResponse.class); }
	@Override
	public ResponseEntity<Log> update(Long id, LogRequest log) {
		
		  Optional<Log> logg = this.logRepository.findById(id);

	        if (logg.isPresent()) {
	        	logg = Optional.ofNullable(log.transformaParaObjeto());
	        	logg.get().setId(id);
	
	            return new ResponseEntity<Log>(logRepository.save(logg.get()),HttpStatus.OK);
	        } 
	        
	            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"); 
	}
}
