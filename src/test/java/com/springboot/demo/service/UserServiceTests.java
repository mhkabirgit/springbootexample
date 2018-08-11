package com.springboot.demo.service;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.springboot.demo.SpringBootDemoApplicationTests;

public class UserServiceTests extends SpringBootDemoApplicationTests{
	
	@Test
	public void testAuthenticationManager(){
		Assert.assertTrue(authenticationManagerBean!=null);
	}
	
	@Test
	public void testTestUser(){
		Assert.assertTrue(userService.userExists(TEST_USERNAME));
	}
	
	@Test
	@Ignore
	public void testGetSessionId(){
		Assert.assertTrue(getSessionId()!=null);
		
	}
	

}
