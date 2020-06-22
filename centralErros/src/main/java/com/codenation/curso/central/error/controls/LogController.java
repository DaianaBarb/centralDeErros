package com.codenation.curso.central.error.controls;

import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import com.codenation.curso.central.error.dto.request.LogRequest;
import com.codenation.curso.central.error.dto.response.LogResponse;
import com.codenation.curso.central.error.models.ErrorLevelsEnum;
import com.codenation.curso.central.error.models.Log;
import com.codenation.curso.central.error.service.interfaces.LogService;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value="/v1/api/central-error/log")
@Api(value = "Central de Erros")

public class LogController {
	
	   @Autowired
	   LogService logService;
	  
	
	   
	
	    @PostMapping()
	    @ApiOperation(value = "Creates a new log")
	    @ApiResponse(code = 201, message = 	"New log successfully created.", response = String.class)
	    @ApiImplicitParams({
	    @ApiImplicitParam(name="Authorization",value="Bearer token", 
      required=true, dataType="string", paramType="header") })
	    
	    public ResponseEntity<LogResponse> saveLog( @Valid @RequestBody LogRequest log){
		 
           return this.logService.save(log);
	    }
	    
	    @PostMapping("/saveAll")
	    @ApiOperation(value = "Creates a new logs")
	    @ApiResponse(code = 201, message = 	"New log successfully created.", response = String.class)
	    @ApiImplicitParams({
	    @ApiImplicitParam(name="Authorization",value="Bearer token", 
     	 required=true, dataType="string", paramType="header") })
	    
	    public ResponseEntity<List<LogResponse>> saveAllLog( @Valid @RequestBody List<LogRequest> logs){
		 
  
           return this.logService.saveAll(logs);

	    }
	    
	   @GetMapping("/{id}")
	   @ApiResponses(value = {
	   @ApiResponse(code = 200, message = 	"returner log successfully", response = String.class),
	   @ApiResponse(code = 204, message = 	"Does not contain log", response = String.class)		})
	   @ApiOperation(value = "Returns log by id")
	   @ApiImplicitParams({
	   @ApiImplicitParam(name="Authorization",value="Bearer token", 
    	 required=true, dataType="string", paramType="header") })
	   
	   public ResponseEntity<Log> findByIdLog(@PathVariable("id") Long id){
		   
		    return this.logService.findById(id);
	   }
	    
       @ApiResponses(value = {
	   @ApiResponse(code = 200, message = "Deleted successfully", response = String.class),
	   @ApiResponse(code = 404, message = 	"Log not found", response = String.class)					})
	   @ApiOperation(value="Deletes a Log.")
	   @DeleteMapping("/{id}")
	   @ApiImplicitParams({
	   @ApiImplicitParam(name="Authorization",value="Bearer token", 
    	 required=true, dataType="string", paramType="header") })
       
       public ResponseEntity<Void> deleteLog(@Valid @PathVariable(value="id") Long id) {
	    	       return this.logService.deleteLog(id);
	    	    }
	   
       @ApiResponses(value = {
       @ApiResponse(code = 200, message = "Update successfully", response = String.class),
       @ApiResponse(code = 404, message = 	"Log not found", response = String.class)					})
       @ApiOperation(value="Update a Log.")
       @PutMapping("/{id}")
       @ApiImplicitParams({
	   @ApiImplicitParam(name="Authorization",value="Bearer token", 
    	 required=true, dataType="string", paramType="header") })
       
       public ResponseEntity<Log> updateLog(@Valid @PathVariable(value="id") Long id, @Valid @RequestBody LogRequest log) {
    		    	    	
    		    	         return this.logService.update(id, log);
    		    	    }
	
	    @ApiResponses(value = {
	    @ApiResponse(code = 200, message = 	"returner logs successfully", response = String.class),
	    @ApiResponse(code = 204, message = 	"Does not contain logs", response = String.class)		})
	    @GetMapping("/getAll_order_by")
	    @ApiOperation(value = "Returns an ordered list of logs")
	    @ApiImplicitParams({
	    @ApiImplicitParam(name="Authorization",value="Bearer token", 
     	 required=true, dataType="string", paramType="header") })
	    
	    public ResponseEntity<List<LogResponse>> sortLogs(@RequestParam(required = false, defaultValue="all") String param,
	    @RequestParam(value = "page", required = false, defaultValue = "0") int page,
	    @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
	         
	    	if(param.equals("all")) {
	    		return this.logService.finfAllLogs(page, size);

	    	}
	    return this.logService.sortLogs(param, page, size);
	    }	    
	            @ApiResponses(value = {
	    	    @ApiResponse(code = 200, message = 	"returner logs successfully", response = String.class),
	    	    @ApiResponse(code = 204, message = 	"Does not contain logs", response = String.class)		})
	    	    @GetMapping("/getAll")
	    	    @ApiOperation(value ="Returns a list of logs according to the requested parameter")
	            @ApiImplicitParams({
	            @ApiImplicitParam(name="Authorization",value="Bearer token", 
	           required=true, dataType="string", paramType="header") })
	        	    
	    	    public ResponseEntity<List<LogResponse>> findAll( @QuerydslPredicate(root = Log.class) Predicate predicate,
	    	    		@ApiParam  @RequestParam(required = false) String origin,
	    	    		@ApiParam  @RequestParam(required =false) ErrorLevelsEnum errorLevel,
	    	    		@ApiParam  @RequestParam(required =false) String description,
	    	    		@ApiParam  @RequestParam(required =false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
	    	    		@ApiParam  @RequestParam(required =false) String logDoEvento,
	    	    		@ApiParam  @RequestParam(required =false)  String  quantity,
	    	            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
	    	            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
	                     
	    	    	

	    	        return this.logService.findAllPredicate(predicate, page, size);
	    	    }
	            
	            
	            
	 
	 
	

	
   
}
