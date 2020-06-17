package com.codenation.curso.central.error.services;

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
import com.codenation.curso.central.error.dto.request.LogRequest;
import com.codenation.curso.central.error.dto.response.LogResponse;
import com.codenation.curso.central.error.models.ErrorLevelsEnum;
import com.codenation.curso.central.error.models.Log;
import com.codenation.curso.central.error.repositories.LogRepository;
import com.codenation.curso.central.error.service.interfaces.LogService;
import com.querydsl.core.types.Predicate;

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
	@Override
	public Page<Log> findAllPredicate(Predicate predicate, int page, int size) {
		Pageable paging = PageRequest.of(page, size);
		
        Page<Log> logs = logRepository.findAll(predicate, paging);
        return logs;
    }
	@Override
	public List<Log> saveAll(List<LogRequest> logs) {
		return logRepository.saveAll(logs.stream().map(log->log
				.transformaParaObjeto())
				.collect(Collectors.toList()));
	}
	@Override
	public Page<Log> findByDate(
			LocalDate date, int page, int size) {
		Pageable paging = PageRequest.of(page, size);
		return logRepository.findByDate(date, paging);
	}
	
	

}
