package com.central.error.services.interfaces;

import java.util.Map;
import org.springframework.data.domain.Page;
import com.central.error.models.Log;



public interface LogService {
	
	  public Log save(Log log);
	  
	  public Page<Log> getLogsList(int page, int size);
	  
	  public Page<Log> sortLogs(String param, int page, int size);
	  
	  public Page<Log> filterLogs(Map<String, String> params, int page, int size);
}
