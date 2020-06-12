package com.mballen.curso.boot.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mballen.curso.boot.dto.request.LogRequest;
import com.mballen.curso.boot.dto.response.LogResponse;
import com.mballen.curso.boot.model.ErrorLevelsEnum;
import com.mballen.curso.boot.model.Log;
import com.mballen.curso.boot.repositories.LogRepository;
import com.mballen.curso.boot.service.interfaces.LogService;





@Service
public class LogServiceImple implements LogService {
	
	@Autowired
    private LogRepository logRepository;

    public Log save(LogRequest log) {
       return  logRepository.save(log.transformaParaObjeto());
          
        
    }
    public Page<Log> getLogsList(int page, int size){
            Page<Log> pageResult = null;
       
            Pageable paging = PageRequest.of(page, size);
            pageResult = logRepository.findAll(paging);
             return pageResult;
    }
    
    public Page<Log> sortLogs(String param, int page, int size) {
        Page<Log> pageResult = null;
        
            Pageable paging = PageRequest.of(page, size, Sort.by(param).ascending());
            pageResult = logRepository.findAll(paging);
            return pageResult;
    }
    
	@Override
	public Optional<Log> findById(Long id) {
		return logRepository.findById(id);
	
	}
	@Override
	public Page<Log> findByErrorLevel(ErrorLevelsEnum errorLevel, int page, int size) {
	 
	 Pageable paging = PageRequest.of(page, size);
		return logRepository.findByErrorLevel(errorLevel, paging);
	}
	@Override
	public Page<Log> findByDate(LocalDate date, int page, int size) {
		
		Pageable paging = PageRequest.of(page, size);
		return logRepository.findByDate(date, paging);
	}
	@Override
	public Page<Log> findByOrigin(String origin, int page, int size) {
		Pageable paging = PageRequest.of(page, size);
		return logRepository.findByOriginIgnoreCase(origin, paging);
	}
	@Override
	public Page<Log> findByDescription(String description, int page, int size) {
		Pageable paging = PageRequest.of(page, size);
		return logRepository.findByDescriptionIgnoreCase(description, paging);
	}
	@Override
	public Page<Log> findByLogDoEvento(String logDoEvento, int page, int size) {
		Pageable paging = PageRequest.of(page, size);
		return logRepository.findByLogDoEventoIgnoreCase(logDoEvento, paging);
	}
	@Override
	public Page<Log> findByQuantity(int quantity, int page, int size) {
		Pageable paging = PageRequest.of(page, size);
		return logRepository.findByQuantity(quantity, paging);

	}
	@Override
	public List<Log> saveAll(List<LogRequest> logs) {
		return logRepository.saveAll(logs.stream().map(log->log
				.transformaParaObjeto())
				.collect(Collectors.toList()));
	}
	@Override
	public Page<Log> finfAllLogs(int page, int size) {
		Pageable paging = PageRequest.of(page, size);
		return logRepository.findAll(paging);
	}
	@Override
	public Log deleteLog(Long id) {
		Optional<Log> log = logRepository.findById(id);
		logRepository.deleteById(id);
		return log.get();
	}
	
	
	
	

}
