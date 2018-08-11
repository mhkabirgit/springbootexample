package com.springboot.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TheErrorController implements ErrorController{
	
	@RequestMapping("/error")
	public ModelAndView handleError(Model model, HttpServletRequest request, HttpServletResponse response, Exception exception){
		
		Integer status = (Integer) request.getAttribute("javax.servlet.error.status_code");
		
		if(status==null){
			status=new Integer(500);
		}
		response.setStatus(status);
		
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.addObject("status", status.toString());
		
		switch(status){
		case 400:
			modelAndView.setViewName("/errors/400");
			break;
		case 404:
			modelAndView.setViewName("/errors/404");
			break;
		case 500:
		default:
			modelAndView.setViewName("/errors/500");
			break;	
		}
		return modelAndView;
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}

}
