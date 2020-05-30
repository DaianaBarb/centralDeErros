package com.mballen.curso.boot.configuration;

import java.io.IOException;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mballen.curso.boot.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
	
		this.authenticationManager = authenticationManager;
	}
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
	// se a autenticação for bem sucesedida o proprio spring vai chamar  o metodo de autenticação bem sucedida. onde sera gerado o token.
			throws AuthenticationException {
		try {
			User user= new ObjectMapper().readValue(request.getInputStream(),User.class);
			return this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserEmail(),user.getPassword()));
		} catch (IOException e) {
		throw new RuntimeException(e);
		}
		
	}
	
@Override
protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) throws IOException, ServletException {
	 //gerando token
	String userName = ((org.springframework.security.core.userdetails.User) 
			authResult.getPrincipal()).getUsername();
	String token=Jwts.builder().setSubject(userName)
			.setExpiration(new Date(System.currentTimeMillis()+ SecurityConstants
					.EXPIRATION_TIME))
			.signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
			.compact();
	String bearerToken =SecurityConstants.TOKEN_PREFIX+token;
	response.getWriter().write(bearerToken); // facilitar o header para o frontEnd colocando o token no body
	response.addHeader(SecurityConstants.HEADER_STRING,bearerToken );
}



}
