package com.codenation.curso.central.error.service.interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import com.codenation.curso.central.error.dto.request.LogRequest;
import com.codenation.curso.central.error.models.Log;
import com.querydsl.core.types.Predicate;

public interface LogService {
	  public Log save(LogRequest  log);
	  public Page<Log> getLogsList(int page, int size);
	  public Page<Log> sortLogs(String param, int page, int size);
	  public Optional<Log> findById(Long id);
	  public Page<Log> finfAllLogs(int page, int size);
	  public List<Log> saveAll(List<LogRequest> logs);
	  public Log deleteLog(Long id);
	  public Page<Log> findAllPredicate(Predicate predicate, int page, int size);
      public Page<Log> findByDate(LocalDate date, int page, int size);
}
