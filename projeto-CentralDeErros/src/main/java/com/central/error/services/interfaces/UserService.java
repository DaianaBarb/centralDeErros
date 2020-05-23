package com.central.error.services.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.mballen.curso.boot.model.User;

public interface UserService {
	 public User getUserInfoByUserEmail(String userEmail);
	 public Optional<User> findById(Long id);
	 public Page<User> getAllUsers(int page, int size);
	 public User save(User user);
     public void deleteUser(Long id);
     public List<User> saveAll(List<User> users);
     
}
