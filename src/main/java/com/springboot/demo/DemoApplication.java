package com.springboot.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


@SpringBootApplication
public class DemoApplication {
	
    public static void main(String[] args) {
//    	SpringApplication.run(DemoApplication.class, args);
    	SpringApplication app=new SpringApplication(DemoApplication.class);
    	app.setShowBanner(true);
    	app.setApplicationContextClass(AnnotationConfigApplicationContext.class);
    	app.setApplicationContextClass(AnnotationConfigEmbeddedWebApplicationContext.class);
    	app.setWebEnvironment(true);
    	app.setAddCommandLineProperties(true);
      app.run(args);
    }
}



