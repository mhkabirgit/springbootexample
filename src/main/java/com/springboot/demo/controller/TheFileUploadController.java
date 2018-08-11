package com.springboot.demo.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/upload")
public class TheFileUploadController {
	
	@RequestMapping(value="singlefile", method=RequestMethod.GET)
	public ModelAndView singleFileUploadGet(){
		return new ModelAndView("/singlefileupload");
	}
	
	@RequestMapping(value="multiplefile", method=RequestMethod.GET)
	public ModelAndView multipleFileUploadGet(){
		return new ModelAndView("/multiplefileupload");
	}
	
	@RequestMapping(value="singlefile", method=RequestMethod.POST)
	public @ResponseBody String singleFileUploadPost(@RequestParam("file") MultipartFile file) throws Exception{
		boolean uploaded=false;
		if(file!=null){
			String fileName=file.getOriginalFilename();
			if(fileName!=null && !fileName.isEmpty()){
				OutputStream os=new FileOutputStream(new File("./src/test/upload/"+fileName));
				os.write(file.getBytes());
				os.close();
				uploaded=true;
			}
		}
		if(uploaded) {
			return new String("Files uploaded.");
		}
		else{
			return new String("You did not submit any file.");
		}
		
	}
	
	@RequestMapping(value="multiplefile", method=RequestMethod.POST)
	public @ResponseBody String multipleFileUploadPost(@RequestParam("filesToUpload") MultipartFile[] files) throws Exception{
		boolean uploaded=false;
		if(files!=null){
			for(MultipartFile file:files){
				String fileName=file.getOriginalFilename();
				if(fileName!=null && !fileName.isEmpty()){
					OutputStream os=new FileOutputStream(new File("./src/test/upload/"+fileName));
					os.write(file.getBytes());
					os.close();
					uploaded=true;
				}
				
			}
			
		}
		if(uploaded) {
			return new String("Files uploaded.");
		}
		else{
			return new String("You did not submit any file.");
		}
		
		
	}

}
