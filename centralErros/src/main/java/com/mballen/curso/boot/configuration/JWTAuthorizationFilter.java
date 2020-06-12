package com.mballen.curso.boot.configuration;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
	private final UserDetailsService userDetailsService;
	
	
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager,UserDetailsService userDetailsService) {
		super(authenticationManager);
	this.userDetailsService= userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String header= request.getHeader(SecurityConstants.HEADER_STRING);
		if(header==null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return ;
		}
		UsernamePasswordAuthenticationToken authentication= getAuthenticationToken(request);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
		// parte de autorização
		//ja passou pela autenticação
		String token= request.getHeader(SecurityConstants.HEADER_STRING);
		if(token==null) return null;
		String userName=Jwts.parser().setSigningKey(SecurityConstants.SECRET)
				.parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX,"")).    // tirar o prefixo que coloquei
				getBody().getSubject();
		UserDetails userDetails= userDetailsService.loadUserByUsername(userName);
		return userName !=null ? new UsernamePasswordAuthenticationToken(userName,null,userDetails.getAuthorities()):null;
	}
}
