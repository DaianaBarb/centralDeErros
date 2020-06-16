package com.codenation.curso.central.error.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String>
{
	@Autowired
	private  UserDetailsService userDetailsService;

	
 @Override
    public Optional<String> getCurrentAuditor(){

    	return Optional.ofNullable(userDetailsService.returnUser());
		
		
		
    }
}