package com.codenation.curso.central.error.configuration;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;




import java.util.ArrayList;

import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
    public Docket productApi() {
		 List<SecurityScheme> auth = new ArrayList<SecurityScheme>();
	      auth.add(new BasicAuth("bearer"));
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.codenation.curso.central.error"))
                .paths(PathSelectors.any()) // qualuer caminho
                .build()
                //.securitySchemes ( matrizes . asList (securityScheme ()))
				//.securityContexts ( matrizes . asList (securityContext ()))
                .apiInfo(metaInfo());
        /*para colocar o token requerido para todos os metodos do controller
         depois de build colocar...
        .globalOperationParameters(Collections
        		.singletonList(new ParameterBuilder()
        				.name("Authorization").description("Barear token").modelRef(new ModelRef("string")).parameterType("header").required(true).build()))
        */
    }

    private ApiInfo metaInfo() {

        ApiInfo apiInfo = new ApiInfo(
                "Central de erros API REST",
                "API REST Logs.",
                "1.0",
                "Terms of Service",
                new Contact("Daiana", "",
                        "Daanabarbosa@hotmail.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/licesen.html", new ArrayList<VendorExtension>()
        );

        return apiInfo;
    }

}