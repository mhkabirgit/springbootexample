package com.springboot.demo.domain.repository;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.springboot.demo.SpringBootDemoApplicationTests;
import com.springboot.demo.domain.User;

public class UserRepositoryTests extends SpringBootDemoApplicationTests{
	
	@Test
	public void testAddAndDeleteAppUserTests(){
		
		User admin=new User("testAdmin", "pass");
		List<String> adminAuthority=new ArrayList();
		adminAuthority.add("ADMIN");
		adminAuthority.add("USER");
		admin.setAuthority(adminAuthority);
		
		userRepo.saveAndFlush(admin);
		
		User rAdmin=userRepo.findByUsernameAndPassword("testAdmin", "pass");
		Assert.assertTrue(rAdmin!=null);
		Assert.assertEquals("testAdmin", rAdmin.getUsername());
		Assert.assertEquals("pass", rAdmin.getPassword());
		Assert.assertEquals("ADMIN", rAdmin.getAuthority().get(0));
		Assert.assertEquals("USER", rAdmin.getAuthority().get(1));
	}

}
