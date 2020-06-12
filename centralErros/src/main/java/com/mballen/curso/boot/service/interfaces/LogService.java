package com.mballen.curso.boot.service.interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import com.mballen.curso.boot.dto.request.LogRequest;
import com.mballen.curso.boot.dto.response.LogResponse;
import com.mballen.curso.boot.model.ErrorLevelsEnum;
import com.mballen.curso.boot.model.Log;

public interface LogService {
	  public Log save(LogRequest  log);
	  public Page<Log> getLogsList(int page, int size);
	  public Page<Log> sortLogs(String param, int page, int size);
	  public Optional<Log> findById(Long id);
	  public Page<Log> finfAllLogs(int page, int size);
	  public Page<Log> findByErrorLevel(ErrorLevelsEnum errorLevel, int page, int size);
	  public Page<Log> findByDate(LocalDate date,  int page, int size);
	  public Page<Log> findByOrigin(String origin,  int page, int size);
	  public Page<Log> findByDescription(String description, int page, int size);
	  public Page<Log> findByLogDoEvento(String logDoEvento, int page, int size);
	  public Page<Log> findByQuantity(int quantity, int page, int size);
	  public List<Log> saveAll(List<LogRequest> logs);
	  public Log deleteLog(Long id);

}
