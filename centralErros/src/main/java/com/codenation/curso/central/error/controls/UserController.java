package com.codenation.curso.central.error.controls;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import com.codenation.curso.central.error.configuration.UserDetailsService;
import com.codenation.curso.central.error.dto.request.UserRequest;
import com.codenation.curso.central.error.dto.response.UserResponse;
import com.codenation.curso.central.error.models.User;
import com.codenation.curso.central.error.service.interfaces.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.NotFoundException;



@RestController
@RequestMapping(value="/user")
@Api(value = "Central de Erros")
public class UserController {
	
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserDetailsService userdetails;

    
	@Autowired
	private RestTemplate restTemplate;
	
	
	@ApiResponses(	value = {
	@ApiResponse(code = 200, message = "Successfully created Token.", response = String.class),
	@ApiResponse(code = 204, message = "Email Invalid ! Usuario nao encontrado ", response = Error.class),
	@ApiResponse(code = 500, message = "Password Invalid!", response = Error.class)	})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value="gerar Token")
	@GetMapping("/gerarToken")
	public ResponseEntity<String> gerarToken(@RequestParam String userEmail, @RequestParam String  password) throws NotFoundException {
    	
    	Optional<User>user = Optional.ofNullable(userService.getUserInfoByUserEmail(userEmail));
    	 if(user.isPresent()) {
    		 
    			 return new ResponseEntity<String> ( restTemplate.postForObject("http://localhost:8080/login", 	 
        	             "{\n" + 
        	    		 "	\"userEmail\":\""+userEmail+"\",\n" + 
        	    		 "	\"password\":\""+password+"\"\n" + 
        	    		 "}", String.class), HttpStatus.OK);	
    		
    	 }
    	     else  throw new ResponseStatusException(HttpStatus.NO_CONTENT, "User not Found");	
	}
	@ApiResponses(value = {
	@ApiResponse(code = 201, message = 	"new user successfully created.", response = String.class)	})
	@ApiOperation(value="Create a new user")
	@PostMapping()
	
	public ResponseEntity<UserResponse> save(@Valid @RequestBody UserRequest usuario) {
		
	return this.userService.save((usuario.transformaParaObjeto()));
	
	}
	@ApiResponses(value = {
	@ApiResponse(code = 201, message = "new users successfully created.", response = String.class),
	@ApiResponse(code = 208, message = "Some user inserted already exists", response = String.class)				})
	@ApiOperation(value="Create a new users")
	@PostMapping("/saveAll")
	
	public ResponseEntity<List<UserResponse>> saveAll(@Valid @RequestBody List<UserRequest> users) {
		return this.userService.saveAll(users);
	    
	}
	 @ApiResponses(value = {
	 @ApiResponse(code = 200, message = 	"returner users successfully", response = String.class),
	 @ApiResponse(code = 204, message = 	"Does not contain users", response = String.class)		})
	 @ApiOperation(value = "Returns a list of registered users.")
	 @GetMapping()
	 @ApiImplicitParams({
     @ApiImplicitParam(name="Authorization",value="Bearer token", 
				 required=true, dataType="string", paramType="header") })
	 
	    public ResponseEntity<List<UserResponse>> getAllUser(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
		 
		return  this.userService.getAllUsers(page, size);
	    }
	    @ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Updated successfully", response = String.class),
	    @ApiResponse(code = 404, message = 	"User not found", response = String.class)		})
	    @ApiOperation(value="Allows you to change a registered user.")
	    @PutMapping("/{id}")
	    @ResponseStatus(HttpStatus.OK)
	    @ApiImplicitParams({
	    @ApiImplicitParam(name="Authorization",value="Bearer token", 
	   				 required=true, dataType="string", paramType="header") })
	    
	    public ResponseEntity<UserResponse> updateUser(@PathVariable(value="id") Long id ,@Valid @RequestBody UserRequest user ) {
	       
	    	return this.userService.updateUser(id, user.transformaParaObjeto());
	    }
	    @ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Deleted successfully", response = String.class),
	    @ApiResponse(code = 404, message = 	"User not found", response = String.class)					})
	    @ApiOperation(value="Deletes a user.")
	    @DeleteMapping("/{id}")
	    @ApiImplicitParams({
	    @ApiImplicitParam(name="Authorization",value="Bearer token", 
	   	 required=true, dataType="string", paramType="header") })
	    
	    public ResponseEntity<Void> deleteUser(@Valid @PathVariable Long id) {
	    	   	
	          return  userService.deleteUser(id);
	    }
	    @ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Successfully found", response = String.class),
	    @ApiResponse(code = 404, message = 	"User not found", response = String.class)					})
	    @ApiOperation(value="find a user by id.")
	    @GetMapping("/{id}")
	    @ResponseStatus(HttpStatus.OK)
	    @ApiImplicitParams({
	    @ApiImplicitParam(name="Authorization",value="Bearer token", 
     	 required=true, dataType="string", paramType="header") })
	    
	    public ResponseEntity<UserResponse> findById(@Valid @PathVariable Long id) {
	    	
	        return this.userService.findById(id);

	    }
	
	 
	 
	

}
