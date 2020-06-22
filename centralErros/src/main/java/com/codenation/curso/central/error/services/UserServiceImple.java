package com.codenation.curso.central.error.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.codenation.curso.central.error.dto.request.UserRequest;
import com.codenation.curso.central.error.dto.response.UserResponse;
import com.codenation.curso.central.error.models.User;
import com.codenation.curso.central.error.repositories.UserRepository;
import com.codenation.curso.central.error.service.interfaces.*;

@Repository
@Transactional
@Service
public class UserServiceImple implements UserService{
	 @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;

    public User getUserInfoByUserEmail(String userEmail) {
        short enabled = 1;
        return userRepository.findByUserEmailAndEnabled(userEmail, enabled);
    }

    public ResponseEntity<UserResponse> findById(Long id) {
    	
    	  Optional<User> user =  this.userRepository.findById(id);

	        if (user.isPresent()) {
	        	
	            return new ResponseEntity<UserResponse> (this.convertToDto(user.get()),HttpStatus.OK);
	        } 
	        else {
	            throw new ResponseStatusException( HttpStatus.NOT_FOUND, "User not found");
	        }
       
    }

    public ResponseEntity<List<UserResponse>> getAllUsers(int page, int size) {
    	 Page<User> pageResult = null;
         Pageable paging = PageRequest.of(page, size);
         pageResult = userRepository.findAll(paging);
         
         
         
		 
	        if (pageResult == null || pageResult.isEmpty()) {
	        	
	        	 throw new ResponseStatusException(HttpStatus.NO_CONTENT,
	        	 		"Does not contain users");
	        }
	      List<UserResponse> dto= (pageResult.stream().map(user->this.convertToDto(user))).collect(Collectors.toList());
          
	      
	      return new ResponseEntity<List<UserResponse>>(dto,HttpStatus.OK);
          
    }

    public ResponseEntity<UserResponse> save(User user) {
    	
    	User userInfo =  this.userRepository.findByUserEmailAndEnabled(user.getUserEmail(),(short) 1);
	
		
	    if (userInfo == null) {
	    
	    user.setPassword(new BCryptPasswordEncoder().encode( user.getPassword()));
	    user =  this.userRepository.save(user);
	    
	    return new ResponseEntity<UserResponse>(this.convertToDto(user), HttpStatus.CREATED);
	   
	    }
	   
            
	    	throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED, "Email already exists, id: " + userInfo.getId());      
    }

    public ResponseEntity<Void> deleteUser(Long id) {
    	 Optional<User> user =  this.userRepository.findById(id);

	        if (user.isPresent()) {
	        	userRepository.deleteById(id);
	        } 
	        else {
	            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
	        }
	        
	        
	        return new ResponseEntity<Void>(HttpStatus.OK);
        
        
    }

	@Override
	public ResponseEntity<List<UserResponse>> saveAll(List<UserRequest> users) {
		

		List<User> listUsers=users.stream().map(user-> user.transformaParaObjeto())
				.filter(user->user.equals(this.userRepository.findByUserEmailAndEnabled(user.getUserEmail(),(short) 1)))
				.map(user-> user).collect(Collectors.toList());
		
		 if(listUsers.isEmpty()) {
			 
	    	listUsers= users.stream().map(usu->usu.transformaParaObjeto()).
	    			map((user)->{
	    		 user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
	    		 return user;
	    	 }).collect(Collectors.toList());
	    	
	    	
	    	this.userRepository.saveAll(listUsers);
	    	return new ResponseEntity<List<UserResponse>>(users.stream().map(usu->this.convertToEntity(usu))
	    			.map(user-> this.convertToDto(user)).collect(Collectors.toList()), HttpStatus.CREATED);
	     }
		 
		 throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED, 
		 		"Some user inserted already exists");	
	
	}
	


	@Override
	public ResponseEntity<UserResponse> updateUser(Long id,User user) {
		
		Optional<User> userInfo = this.userRepository.findById(id);

        if (userInfo.isPresent()) {
        	
        	userInfo.get().setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        	userInfo.get().setName(user.getName());
        	userInfo.get().setUserEmail(user.getUserEmail());
        	 this.userRepository.save(userInfo.get());
        	 return new ResponseEntity<UserResponse>( this.convertToDto(userInfo.get()),HttpStatus.OK);
            
        }
        else{
            throw new ResponseStatusException( HttpStatus.NOT_FOUND, "User not found");
        }
        
       
		
	}
	private UserResponse convertToDto(User user) { return modelMapper.map(user, UserResponse.class); }
	  private User convertToEntity(UserRequest userDTORequest) { return modelMapper.map(userDTORequest, User.class); }
}
