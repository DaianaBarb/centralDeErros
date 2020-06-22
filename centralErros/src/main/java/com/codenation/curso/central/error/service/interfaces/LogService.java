package com.codenation.curso.central.error.service.interfaces;


import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import com.codenation.curso.central.error.dto.request.LogRequest;
import com.codenation.curso.central.error.dto.response.LogResponse;
import com.codenation.curso.central.error.models.Log;
import com.querydsl.core.types.Predicate;

public interface LogService {
	
	  public ResponseEntity<LogResponse> save(LogRequest  log);
	  public Page<Log> getLogsList(int page, int size);
	  public ResponseEntity <List<LogResponse>> sortLogs(String param, int page, int size);
	  public ResponseEntity<Log> findById(Long id);
	  public ResponseEntity<List<LogResponse>> finfAllLogs(int page, int size);
	  public ResponseEntity<List<LogResponse>> saveAll(List<LogRequest> logs);
	  public ResponseEntity<Void> deleteLog(Long id);
	  public ResponseEntity<List<LogResponse>> findAllPredicate(Predicate predicate, int page, int size);
	  public ResponseEntity<Log> update(Long id, LogRequest log);
     
}
