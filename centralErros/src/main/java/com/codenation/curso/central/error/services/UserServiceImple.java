package com.codenation.curso.central.error.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codenation.curso.central.error.models.User;
import com.codenation.curso.central.error.repositories.UserRepository;
import com.codenation.curso.central.error.service.interfaces.*;

@Repository
@Transactional
@Service
public class UserServiceImple implements UserService{

    @Autowired
    private UserRepository userRepository;

    public User getUserInfoByUserEmail(String userEmail) {
        short enabled = 1;
        return userRepository.findByUserEmailAndEnabled(userEmail, enabled);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Page<User> getAllUsers(int page, int size) {
    	 Page<User> pageResult = null;
         Pageable paging = PageRequest.of(page, size);
         pageResult = userRepository.findAll(paging);
          return pageResult;
    }

    public User save(User user) {
       // user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

	@Override
	public List<User> saveAll(List<User> users) {
		
		return userRepository.saveAll(users);
	}
}
