package com.mballen.curso.boot.configuration;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.mballen.curso.boot.repositories.UserRepository;




@Component
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
	@Autowired
	UserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
		com.mballen.curso.boot.model.User usuario = repository.findByUserEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("No existe usuario"));
		List<GrantedAuthority> authorityListAdmin = AuthorityUtils.createAuthorityList("ROLE_USER","ROLE_ADMIN");
		List<GrantedAuthority> authorityListUser = AuthorityUtils.createAuthorityList("ROLE_USER");
	    
		return new User(usuario.getUserEmail(),usuario.getPassword(),authorityListAdmin);
	   
	}

	
}
