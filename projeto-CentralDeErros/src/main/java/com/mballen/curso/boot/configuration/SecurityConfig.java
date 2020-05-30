package com.mballen.curso.boot.configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	

	@Autowired
	private UserDetailsService userDetailsService;
/*
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable().authorizeRequests()
				.antMatchers(HttpMethod.GET, "/user/save","/login","/swagger-ui.html#/user-controller/saveUsingPOST").permitAll().anyRequest()
				.authenticated().and().exceptionHandling()// as demais paginas precisam de  autenticação
				.and().formLogin().permitAll().usernameParameter("login").passwordParameter("senha")
				.failureUrl("/login?error=true")
				.defaultSuccessUrl("/swagger-ui.html").and().logout().permitAll().deleteCookies("JSESSIONID")
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout=true").and()
				.sessionManagement().maximumSessions(1) // usuario so pode logar uma unica vez
				.maxSessionsPreventsLogin(true).expiredUrl("/login?expired=true").sessionRegistry(sessionRegistry());
		

	}*/
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().configurationSource(request->new CorsConfiguration().applyPermitDefaultValues()) // libera a Api para ser acessada por clientes em outros servidores (recursos que n estao no mesmo servidor)
		.and().csrf()
		.disable()
		.authorizeRequests()
		.antMatchers(HttpMethod.GET,SecurityConstants.SING_UP_URL)
		.permitAll()
		.and().addFilter(new JWTAuthenticationFilter(authenticationManager()))  // adicionou um filtro
		.addFilter(new JWTAuthorizationFilter(authenticationManager(), userDetailsService()));
		

	}
	
	
	@Bean
	public SessionRegistry sessionRegistry() {
		SessionRegistry sessionRegistry = new SessionRegistryImpl();
		return sessionRegistry;
	}
	@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
}

	


	
}
