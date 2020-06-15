package com.codenation.curso.central.error.configuration;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableTransactionManagement
@EnableJpaAuditing ( auditorAwareRef =  "auditorAware" ) 
public class PersistenceConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
  
    @Bean
	public RestTemplate restemplate(){
		return new RestTemplate();
	}

    @Bean
    public AuditorAware<String> auditorAware(){
        return new AuditorAwareImpl();
    } 
}
