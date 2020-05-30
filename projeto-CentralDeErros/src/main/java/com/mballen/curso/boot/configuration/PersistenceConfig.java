package com.mballen.curso.boot.configuration;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

//import com.mballen.curso.boot.services.UserDetailsServiceImpl;







@Configuration
@EnableTransactionManagement
public class PersistenceConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
   /* @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }*/
 @Bean 
 public HttpEntity httpEntity() {
	return new  HttpEntity(null);
 }
    @Bean
	public RestTemplate restemplate(){
		return new RestTemplate();
	}

}
