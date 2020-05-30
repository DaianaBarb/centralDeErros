package com.mballen.curso.boot.service.interfaces;

import java.util.Map;

import org.springframework.data.domain.Page;

import com.mballen.curso.boot.model.Log;

public interface LogService {
	  public Log save(Log log);
	  public Page<Log> getLogsList(int page, int size);
	  public Page<Log> sortLogs(String param, int page, int size);
	  public Page<Log> filterLogs(Map<String, String> params, int page, int size);
}
