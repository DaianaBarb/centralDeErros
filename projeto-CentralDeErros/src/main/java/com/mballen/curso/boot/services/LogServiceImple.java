package com.mballen.curso.boot.services;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.mballen.curso.boot.model.Log;
import com.mballen.curso.boot.repositories.LogRepository;
import com.mballen.curso.boot.service.interfaces.LogService;
import com.mballen.curso.boot.specification.LogSpecs;




@Service
public class LogServiceImple implements LogService {
	
	@Autowired
    private LogRepository logRepository;

    public Log save(Log log) {
       return  logRepository.save(log);
          
        
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
    public Page<Log> filterLogs(Map<String, String> params, int page, int size) {
        Page<Log> pageResult = null;
        
            Pageable paging = PageRequest.of(page, size);
            pageResult = logRepository.findAll(LogSpecs.getLogsByFilters(params), paging);
           
           return pageResult;
    }

}
