package com.codenation.curso.central.error.service.interfaces;

import java.util.List;
import org.springframework.http.ResponseEntity;
import com.codenation.curso.central.error.dto.request.UserRequest;
import com.codenation.curso.central.error.dto.response.UserResponse;
import com.codenation.curso.central.error.models.User;

public interface UserService {
	 public User getUserInfoByUserEmail(String userEmail);
	 public ResponseEntity<UserResponse> findById(Long id);
	 public ResponseEntity<List<UserResponse>> getAllUsers(int page, int size);
	 public ResponseEntity<UserResponse> save(User user);
     public ResponseEntity<Void> deleteUser(Long id);
     public ResponseEntity<List<UserResponse>> saveAll(List<UserRequest> users);
     public ResponseEntity<UserResponse> updateUser(Long id,User user);
     
}
