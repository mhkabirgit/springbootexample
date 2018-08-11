package com.springboot.demo;


import javax.servlet.MultipartConfigElement;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class DemoMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
    }
    
    @Bean
    public MultipartConfigElement multipartConfigElement(){
    	MultipartConfigFactory factory=new MultipartConfigFactory();
    	factory.setFileSizeThreshold("1024KB");
    	factory.setMaxFileSize("1024KB");
    	factory.setMaxRequestSize("1024KB");
    	return factory.createMultipartConfig();
    }
    
    @Bean
	public Validator getBeanValidator() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		return factory.getValidator();
	}
    
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(true).ignoreAcceptHeader(false)
				.useJaf(true).mediaType("json", MediaType.APPLICATION_JSON).mediaType("xml", MediaType.APPLICATION_XML);
	}
    
}

