package com.mballen.curso.boot.configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.mballen.curso.boot.model.User;

import java.util.Optional;

public class AuditorAwareImpl // implements AuditorAware<String>
{

  //  @Override
    public Optional<String> getCurrentAuditor(){
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return Optional.ofNullable(((User) auth.getPrincipal()).getUserEmail());
    }
}
