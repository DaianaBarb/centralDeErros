package com.mballen.curso.boot.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mballen.curso.boot.dto.request.LogRequest;
import com.mballen.curso.boot.dto.response.LogResponse;
import com.mballen.curso.boot.model.ErrorLevelsEnum;
import com.mballen.curso.boot.model.Log;
import com.mballen.curso.boot.model.User;
import com.mballen.curso.boot.repositories.LogRepository;
import com.mballen.curso.boot.service.interfaces.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value="/api")
@Api(value = "Central de Erros")
public class LogController {
	   @Autowired
	   LogService logService;
	    @Autowired	
       LogRepository repository;
	  //faz conversao de dados
	    @Autowired(required=true)
	    private ModelMapper modelMapper;
	
	    @PostMapping("/save")
	    @ApiOperation(value = "Creates a new log")
	    @ApiResponse(code = 201, message = 	"New log successfully created.", response = String.class)
	    
	    public ResponseEntity<LogResponse> saveLog( @Valid @RequestBody LogRequest log){
		 
            Log logCreated = logService.save(log);
            
	        return new ResponseEntity<LogResponse> (convertToDto(logCreated),HttpStatus.CREATED);
	    }
	    
	    @PostMapping("/saveAll")
	    @ApiOperation(value = "Creates a new log")
	    @ApiResponse(code = 201, message = 	"New log successfully created.", response = String.class)
	    public ResponseEntity<List<LogResponse>> saveAllLog( @Valid @RequestBody List<LogRequest> logs){
		 
           List< Log> logsCreated = logService.saveAll(logs);
            
           return new ResponseEntity<List<LogResponse>>(  logsCreated.stream().map(this::convertToDto).collect(Collectors.toList()),HttpStatus.CREATED);

	    }

	   /* @GetMapping()
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
	 */
	 
	 
	   @GetMapping("logs/{id}")
	   @ApiResponses(value = {
	   @ApiResponse(code = 200, message = 	"returner log successfully", response = String.class),
	   @ApiResponse(code = 204, message = 	"Does not contain log", response = String.class)		})
	   @ApiOperation(value = "Returns a list of logs or returns a list of logs according to the requested filters")
	  public ResponseEntity<Log> findByIdLog(@PathVariable("id") Long id){
		  Optional<Log> log = logService.findById(id);
		   if(log.isPresent()) {
			   return new  ResponseEntity<Log>(log.get(),HttpStatus.OK);
		   }
		   
		   return new ResponseEntity<Log>(HttpStatus.NOT_FOUND);
	   }
	   
	    @ApiResponses(value = {
	    @ApiResponse(code = 200, message = 	"returner find_by_errorlevel successfully", response = String.class),
	    @ApiResponse(code = 204, message = 	"does not contain the log with the specified parameter", response = String.class)		})
	    @GetMapping("/logs/find_by_errorlevel")
	    @ApiOperation(value = "Returns a list of logs according to the requested filters")
	    
	    public ResponseEntity<List<LogResponse>> findLogsErrorLevel(
	    		@RequestParam(required = true) ErrorLevelsEnum errorLevel,
	            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
	            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
	    	
	        Page<Log> logs = logService.findByErrorLevel(errorLevel, page, size);
	        if (logs.isEmpty()) {
	        	return new ResponseEntity<List<LogResponse>>(HttpStatus.NOT_FOUND);
	        }
	    	return new ResponseEntity<List<LogResponse>>(  logs.stream().map(this::convertToDto).collect(Collectors.toList()),HttpStatus.ACCEPTED);

	    }
	    @ApiResponses(value = {
	    @ApiResponse(code = 200, message = 	"returner find_by_origin successfully", response = String.class),
	    @ApiResponse(code = 204, message = 	"does not contain the log with the specified parameter", response = String.class)		})
	    @GetMapping("/logs/find_by_origin")
	    @ApiOperation(value = "Returns a list of logs according to the requested filters")
	    
	    public ResponseEntity<List<LogResponse>> findLogsOrigin(
	    @RequestParam(required = true) String origin,
	    @RequestParam(value = "page", required = false, defaultValue = "0") int page,
	    @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
	    	
	    	Page<Log> logs = logService.findByOrigin(origin, page, size);
	    	  if (logs.isEmpty()) {
		        	return new ResponseEntity<List<LogResponse>>(HttpStatus.NOT_FOUND);
		        }
	        logs.stream().map(this::convertToDto).collect(Collectors.toList());
	    	return new ResponseEntity<List<LogResponse>>(  logs.stream().map(this::convertToDto).collect(Collectors.toList()),HttpStatus.ACCEPTED);

	    }
	    @ApiResponses(value = {
	    @ApiResponse(code = 200, message = 	"returner find_by_date successfully", response = String.class),
	    @ApiResponse(code = 204, message = 	"does not contain the log with the specified parameter", response = String.class)		})
	    @GetMapping("/logs/find_by_date")
	    @ApiOperation(value = "Returns a list of logs or returns a list of logs according to the requested filters")
	    
	    public ResponseEntity<List<LogResponse>> findLogsDate(
	    @RequestParam(required = true) String date,
	    @RequestParam(value = "page", required = false, defaultValue = "0") int page,
	    @RequestParam(value = "size", required = false, defaultValue = "10") int size) { 
	    	
	    	Page<Log> logs = logService.findByDate(LocalDate.parse(date), page, size);
	    	  if (logs.isEmpty()) {
		        	return new ResponseEntity<List<LogResponse>>(HttpStatus.NOT_FOUND);
		        }
	        logs.stream().map(this::convertToDto).collect(Collectors.toList());
	    	return new ResponseEntity<List<LogResponse>>(  logs.stream().map(this::convertToDto).collect(Collectors.toList()),HttpStatus.ACCEPTED);

	    } 
	    @ApiResponses(value = {
	    @ApiResponse(code = 200, message = 	"returner find_by_logdoevento successfully", response = String.class),
	    @ApiResponse(code = 204, message = 	"does not contain the log with the specified parameter", response = String.class)		})
	    @GetMapping("/logs/find_by_logdoevento")
	    @ApiOperation(value = "returns a list of logs according to the requested filters")
	    
	    public ResponseEntity<List<LogResponse>> findLogsLogDoEvento(
	    @RequestParam(required = true) String logDoEvento,
	    @RequestParam(value = "page", required = false, defaultValue = "0") int page,
	    @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
	    	
	    	Page<Log> logs = logService.findByLogDoEvento(logDoEvento, page, size);
	    	  if (logs.isEmpty()) {
		        	return new ResponseEntity<List<LogResponse>>(HttpStatus.NOT_FOUND);
		        }
	        logs.stream().map(this::convertToDto).collect(Collectors.toList());
	    	return new ResponseEntity<List<LogResponse>>(  logs.stream().map(this::convertToDto).collect(Collectors.toList()),HttpStatus.ACCEPTED);

	    } 
	    @ApiResponses(value = {
	    @ApiResponse(code = 200, message = 	"returner find_by_description successfully", response = String.class),
	    @ApiResponse(code = 204, message = 	"does not contain the log with the specified parameter", response = String.class)		})
	    @GetMapping("/logs/find_by_description")
	    @ApiOperation(value = "returns a list of logs according to the requested filters")
	    
	    public ResponseEntity<List<LogResponse>> findLogsDescription(
	   	@RequestParam(required = true) String description,
	    @RequestParam(value = "page", required = false, defaultValue = "0") int page,
	    @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
	    	
	    Page<Log> logs = logService.findByDescription(description, page, size);
	    if (logs.isEmpty()) {
        	return new ResponseEntity<List<LogResponse>>(HttpStatus.NOT_FOUND);
        }
	    logs.stream().map(this::convertToDto).collect(Collectors.toList());
	    	return new ResponseEntity<List<LogResponse>>(  logs.stream().map(this::convertToDto).collect(Collectors.toList()),HttpStatus.ACCEPTED);

	    }
	    @ApiResponses(value = {
	    @ApiResponse(code = 200, message = 	"returner find_by_quantity successfully", response = String.class),
	    @ApiResponse(code = 204, message = 	"does not contain the log with the specified parameter", response = String.class)		})
	    @GetMapping("/logs/find_by_quantity")
	    @ApiOperation(value = "returns a list of logs according to the requested filters")
	    
	    public ResponseEntity<List<LogResponse>> findLogsQuantity(
	    @RequestParam(required = true) int quantity,
	    @RequestParam(value = "page", required = false, defaultValue = "0") int page,
	    @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
	    	
	    Page<Log> logs = logService.findByQuantity(quantity, page, size);
	    if (logs.isEmpty()) {
        	return new ResponseEntity<List<LogResponse>>(HttpStatus.NOT_FOUND);
        }
	    logs.stream().map(this::convertToDto).collect(Collectors.toList());
	    	return new ResponseEntity<List<LogResponse>>(  logs.stream().map(this::convertToDto).collect(Collectors.toList()),HttpStatus.ACCEPTED);

	    }
	    @ApiResponses(value = {
	    @ApiResponse(code = 200, message = 	"returner logs successfully", response = String.class),
	    @ApiResponse(code = 204, message = 	"Does not contain logs", response = String.class)		})
	    @GetMapping("/logs/getAll_order_by")
	    @ApiOperation(value = "Returns an ordered list of logs")
	    
	    public ResponseEntity<List<LogResponse>> sortLogs(@RequestParam(required = false, defaultValue="all") String param,
	    @RequestParam(value = "page", required = false, defaultValue = "0") int page,
	    @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
	         
	    	if(param.equals("all")) {
	    		Page<Log>  logs = logService.finfAllLogs(page, size);
    	        Page<LogResponse> pageLog = new PageImpl<>(logs.getContent().stream()
    	                .map(this::convertToDto)
    	                .collect(Collectors.toList()));

    	        return new ResponseEntity <List<LogResponse>>(pageLog.getContent(),HttpStatus.ACCEPTED);
	    	}
	    	
	    	Page<Log>  logs = logService.sortLogs(param, page, size);
	        Page<LogResponse> pageLog = new PageImpl<>(logs.getContent().stream()
	                .map(this::convertToDto)
	                .collect(Collectors.toList()));

	        return new ResponseEntity <List<LogResponse>>(pageLog.getContent(),HttpStatus.ACCEPTED);
	    }
	    @ApiResponses(value = {
	    	    @ApiResponse(code = 200, message = "Deleted successfully", response = String.class),
	    	    @ApiResponse(code = 404, message = 	"Log not found", response = String.class)					})
	    	    @ApiOperation(value="Deletes a Log.")
	    	    @DeleteMapping("/{id}")
	    	    public ResponseEntity<Log> deleteLog(@Valid @PathVariable Long id) {
	    	    	
	    	        Optional<Log> log = this.logService.findById(id);

	    	        if (log.isPresent()) {
	    	        	
	    	            
	    	            return new ResponseEntity<Log>(this.logService.deleteLog(id),HttpStatus.OK);
	    	        } 
	    	        
	    	            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
	    	        
	    	        
	    	        
	    	        
	    	    }
	    
	            /*@ApiResponses(value = {
	    	    @ApiResponse(code = 200, message = 	"returner logs successfully", response = String.class),
	    	    @ApiResponse(code = 204, message = 	"Does not contain logs", response = String.class)		})
	    	    @GetMapping("/logs/getAll")
	    	    @ApiOperation(value = "Returns an ordered list of logs")
	    	    
	    	    public ResponseEntity<List<LogResponse>> findAll(
	    	    @RequestParam(value = "page", required = false, defaultValue = "0") int page,
	    	    @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
	    	       
	    	    	Page<Log>  logs = logService.finfAllLogs(page, size);
	    	        Page<LogResponse> pageLog = new PageImpl<>(logs.getContent().stream()
	    	                .map(this::convertToDto)
	    	                .collect(Collectors.toList()));

	    	        return new ResponseEntity <List<LogResponse>>(pageLog.getContent(),HttpStatus.ACCEPTED);
	    	    }
	    */
	    
	 private LogResponse convertToDto(Log log) { return modelMapper.map(log, LogResponse.class); }
	 
	 private Log convertToEntity(LogResponse logDtoRequest) { return modelMapper.map(logDtoRequest, Log.class); }

	
   
}
