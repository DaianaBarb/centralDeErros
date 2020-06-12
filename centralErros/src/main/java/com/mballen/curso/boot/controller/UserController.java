package com.mballen.curso.boot.controller;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import com.mballen.curso.boot.configuration.UserDetailsService;
import com.mballen.curso.boot.dto.request.UserRequest;
import com.mballen.curso.boot.dto.response.UserResponse;
import com.mballen.curso.boot.model.User;
import com.mballen.curso.boot.service.interfaces.UserService;
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
	
	private static final PasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserDetailsService userdetails;
	
    @Autowired
    private ModelMapper modelMapper;
    
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
		
		User userInfo =  this.userService.getUserInfoByUserEmail(usuario.getUserEmail());
		User user;
		
	    if (userInfo == null) {
	    
	    usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
	    user =  this.userService.save(usuario.transformaParaObjeto());
        
	    }
	    else {
            
	    	throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED, "Email already exists, id: " + userInfo.getId());
        }
	    
	    
	    return new ResponseEntity<>(UserResponse.transformaEmDTO(user), HttpStatus.CREATED);
	}
	@ApiResponses(value = {
	@ApiResponse(code = 201, message = "new users successfully created.", response = String.class),
	@ApiResponse(code = 208, message = "Some user inserted already exists", response = String.class)				})
	@ApiOperation(value="Create a new users")
	@PostMapping("/all")
	public ResponseEntity<List<UserResponse>> saveAll(@Valid @RequestBody List<UserRequest> users) {
		
		
		List<User> listUsers=users.stream().map(user-> user.transformaParaObjeto())
				.filter(user->user.equals(this.userService.getUserInfoByUserEmail(user.getUserEmail())))
				.map(user-> user).collect(Collectors.toList());
		
		 if(listUsers.isEmpty()) {
			 
	    	listUsers= users.stream().map(usu->usu.transformaParaObjeto()).
	    			map((user)->{
	    		 user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
	    		 return user;
	    	 }).collect(Collectors.toList());
	    	
	    	
	    	this.userService.saveAll(listUsers);
	    	return new ResponseEntity<List<UserResponse>>(users.stream().map(usu->this.convertToEntity(usu))
	    			.map(user-> this.convertToDto(user)).collect(Collectors.toList()), HttpStatus.CREATED);
	     }
		 
		 throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED, 
		 		"Some user inserted already exists");
	    
	}
	 @ApiResponses(value = {
	 @ApiResponse(code = 200, message = 	"returner users successfully", response = String.class),
	 @ApiResponse(code = 204, message = 	"Does not contain users", response = String.class)		})
	 @ApiOperation(value = "Returns a list of registered users.")
	 @GetMapping("/users")
	 @ApiImplicitParams({
     @ApiImplicitParam(name="Authorization",value="Bearer token", 
				 required=true, dataType="string", paramType="header") })
	    public ResponseEntity<List<UserResponse>> getAllUser(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
		 
		 Page<User> users =  this.userService.getAllUsers(page,size);
		 
	        if (users == null || users.isEmpty()) {
	        	
	        	 throw new ResponseStatusException(HttpStatus.NO_CONTENT,
	        	 		"Does not contain users");
	        }
	      List<UserResponse> dto= users.stream().map(user->this.convertToDto(user)).collect(Collectors.toList());
             
	      
	      return new ResponseEntity<List<UserResponse>>(dto,HttpStatus.OK);
	    }
	    @ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Updated successfully", response = String.class),
	    @ApiResponse(code = 404, message = 	"User not found", response = String.class)		})
	    @ApiOperation(value="Allows you to change a registered user.")
	    @PutMapping()
	    @ResponseStatus(HttpStatus.OK)
	    public ResponseEntity<UserResponse> updateUser(@Valid @RequestBody User user ) {
	       
	        Optional<User> userInfo = this.userService.findById(user.getId());

	        if (userInfo.isPresent()) {
	        	
	        	userInfo.get().setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
	        	 this.userService.save(userInfo.get());
	            
	        }
	        else{
	            throw new ResponseStatusException( HttpStatus.NOT_FOUND, "User not found");
	        }
	        
	        return new ResponseEntity<UserResponse>( this.convertToDto(userInfo.get()),HttpStatus.OK);
	    }
	    @ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Deleted successfully", response = String.class),
	    @ApiResponse(code = 404, message = 	"User not found", response = String.class)					})
	    @ApiOperation(value="Deletes a user.")
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteUser(@Valid @PathVariable Long id) {
	    	
	        Optional<User> user =  this.userService.findById(id);

	        if (user.isPresent()) {
	        	
	            userService.deleteUser(id);
	        } 
	        else {
	            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
	        }
	        
	        
	        return new ResponseEntity<Void>(HttpStatus.OK);
	    }
	    @ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Successfully found", response = String.class),
	    @ApiResponse(code = 404, message = 	"User not found", response = String.class)					})
	    @ApiOperation(value="find a user by id.")
	    @GetMapping("/{id}")
	    @ResponseStatus(HttpStatus.OK)
	    public ResponseEntity<UserResponse> findByUser(@Valid @PathVariable Long id) {
	    	
	        Optional<User> user =  this.userService.findById(id);

	        if (user.isPresent()) {
	        	
	            return new ResponseEntity<UserResponse> (this.convertToDto(user.get()),HttpStatus.OK);
	        } 
	        else {
	            throw new ResponseStatusException( HttpStatus.NOT_FOUND, "User not found");
	        }
	    }
	
	 
	 
	private void encodeAll(List<User> users) {
		users.forEach(this::encode);
	}
	private void encode(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
	}
	private UserResponse convertToDto(User user) { return modelMapper.map(user, UserResponse.class); }
    private User convertToEntity(UserRequest userDTORequest) { return modelMapper.map(userDTORequest, User.class); }

}
