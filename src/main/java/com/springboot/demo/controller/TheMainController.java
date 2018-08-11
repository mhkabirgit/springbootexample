package com.springboot.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TheMainController {
	
	@RequestMapping("/")
	public ModelAndView handleInternalRequest(){
		return new ModelAndView("home");
	}
	
	@RequestMapping("/home")
	public ModelAndView home(){
		return new ModelAndView("home");
	}
	
//	@RequestMapping ("/login")
//	public ModelAndView login(){
//		return new ModelAndView("login");
//	}

	@RequestMapping("main")
	public ModelAndView hello(){
		return new ModelAndView("main");
	}
	
	@RequestMapping("welcome")
	public String welcome(){
		return "Welcome to Spring Boot Demo";
	}
	
	@RequestMapping("jsontest")
	public ModelAndView jsontest(){
		return new ModelAndView("jsontest");
	}
	
	@RequestMapping("testException")
	public Exception throwTestException() throws Exception{
		throw new Exception("The Test Exception");
	}
	
	
	
}
