package com.mballen.curso.boot.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.mballen.curso.boot.dto.request.LogRequest;
import com.mballen.curso.boot.model.Log;
import com.mballen.curso.boot.service.interfaces.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/api")
@Api(value = "Central de Erros")
public class LogController {
	@Autowired
	LogService logService;

	  //faz conversao de dados
	 @Autowired(required=true)
	    private ModelMapper modelMapper;
	
	 @PostMapping()
	    @ApiOperation(value = "Creates a new log")
	    public ResponseEntity<Log> saveLog( @Valid @RequestBody Log log){
		 
	       
            Log logCreated = logService.save(log);
            
	        return new ResponseEntity<Log> (logCreated,HttpStatus.CREATED);
	    }

	    @GetMapping()
	    @ApiOperation(value = "Returns a list of logs")
	    public ResponseEntity<List<LogRequest>> getLogs(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
	                                   @RequestParam(value = "size", required = false, defaultValue = "10") int size){

	        Page<Log> logs = logService.getLogsList(page, size);
	        List<LogRequest> listLogdTO =
	        		 logs.stream()
	                .map(this::convertToDto)
	                .collect(Collectors.toList());

	        return  new ResponseEntity<List<LogRequest>> (listLogdTO,HttpStatus.ACCEPTED);
	    }
	 
	   

	    @GetMapping("/logs/find_by")
	    @ApiOperation(value = "Returns a list of logs according to the requested filters")
	    public ResponseEntity<List<LogRequest>> findLogs(@RequestParam Map<String,String> allParams,
	                                        @RequestParam(value = "page", required = false, defaultValue = "0") int page,
	                                        @RequestParam(value = "size", required = false, defaultValue = "10") int size) {

	        Page<Log> logs = logService.filterLogs(allParams, page, size);
	        List<LogRequest> listLogdTO = logs.stream()
	                .map(this::convertToDto)
	                .collect(Collectors.toList());

	        Page<LogRequest> pageLog = new PageImpl<>(listLogdTO);

	        return new ResponseEntity<List<LogRequest>> ( pageLog.getContent(),HttpStatus.ACCEPTED);

	    }

	    @GetMapping("/logs/order_by")
	    @ApiOperation(value = "Returns an ordered list of logs")
	    public ResponseEntity<List<LogRequest>> sortLogs(@RequestParam Map<String,String> param,
	                                        @RequestParam(value = "page", required = false, defaultValue = "0") int page,
	                                        @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
	        String paramName = getParameterSort(param);

	        Page<Log> logs = logService.sortLogs(paramName, page, size);

	        List<LogRequest> listLogdTO = logs.getContent().stream()
	                .map(this::convertToDto)
	                .collect(Collectors.toList());

	        Page<LogRequest> pageLog = new PageImpl<>(listLogdTO);

	        return new ResponseEntity <List<LogRequest>>(pageLog.getContent(),HttpStatus.ACCEPTED);
	    }
	    
	 private LogRequest convertToDto(Log log) { return modelMapper.map(log, LogRequest.class); }
	 
	 private Log convertToEntity(LogRequest logDtoRequest) { return modelMapper.map(logDtoRequest, Log.class); }

	 private static String getParameterSort(Map<String, String> params) { return params.get("order_by");}
     // metodo que pega o valor da chave order_by para ser inserido no sortLogs
}
