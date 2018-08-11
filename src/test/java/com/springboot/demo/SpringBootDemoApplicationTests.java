package com.springboot.demo;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

//import org.apache.http.HttpHeaders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.springboot.demo.domain.repository.UserRepository;
import com.springboot.demo.service.UserService;


@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(value = "test")
@SpringApplicationConfiguration(classes = DemoApplication.class)
@WebIntegrationTest ("server.port:8080")

//@IntegrationTest("server.port:8080")
//@WebAppConfiguration
@Transactional
public class SpringBootDemoApplicationTests {
	
	public static final String TEST_USERNAME = "testuser";
	public static final String TEST_USER_PASSWORD = "passw0rd";
	
	@Autowired
	protected Environment environment; 
	
	@Autowired
	protected UserService userService;
	
	@Autowired
	protected AuthenticationManager authenticationManagerBean;
		
	@Autowired
	protected UserRepository userRepo;
	
	@Autowired
	protected PasswordEncoder passwordEncoder;
	
	@Value("${profile.name}")
	private String profileName;
	
	private RestTemplate authenticatedTemplate;
	/**
	 * @return the environment
	 */
	public Environment getEnvironment() {
		return environment;
	}

	/**
	 * @return the profileName
	 */
	public String getProfileName() {
		return profileName;
	}
	
	
	@Test
	public void contextLoads() {
		
	}
	
	@AfterTransaction
	public void deleteTestUser(){
		userService.deleteUser(TEST_USERNAME);
	}
	
	@BeforeTransaction
	 public void createTestUser() {
		if (!userService.userExists(TEST_USERNAME)) {
			
			// create the test user.
			userService.createUser(TEST_USERNAME, TEST_USER_PASSWORD, Arrays.asList("USER"), true);
		}
	 }
	 
	
	 public RestTemplate getAuthenticatedRestTemplate() {
		 
		 if(this.authenticatedTemplate==null){
			 this.authenticatedTemplate = new RestTemplate();
			 String jSessionId = getSessionId();
			// create the interceptor that adds the JSESSIONID header
			ClientHttpRequestInterceptor interceptor = new SessionIdInterceptor(jSessionId);
			authenticatedTemplate.setInterceptors(Collections.singletonList(interceptor));		
		 }
		
		return authenticatedTemplate;
	 }

	/**
	 * The main login page is used to log in a valid test user and retrieve a
	 * valid session.
	 * 
	 * @return a valid session id.
	 * @throws URISyntaxException 
	 * @throws RestClientException 
	 */
	public String getSessionId() {
		if (!userService.userExists(TEST_USERNAME)) {
			
			// create the test user.
			userService.createUser(TEST_USERNAME, TEST_USER_PASSWORD, Arrays.asList("USER"), true);
		}
		
//		MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
	
		params.add("username", TEST_USERNAME);
		params.add("password", TEST_USER_PASSWORD);
				
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		

		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params,requestHeaders);

		
//		ResponseEntity<HttpServletResponse> response = new RestTemplate().postForEntity("http://localhost:8080/login",
//				params, HttpServletResponse.class);
		
		ResponseEntity<HttpServletResponse> response = 
				new RestTemplate().postForEntity("http://localhost:8080/login",
				httpEntity, HttpServletResponse.class);
		
		HttpHeaders headers = response.getHeaders();
		Set<String> keys = headers.keySet();
		
		
		String cookie = "";

		for (String header : keys) {
			if (header.equals("Set-Cookie")) {
				cookie = headers.get(header).get(0);
				
			}
		}

		return cookie.split(";")[0].split("=", 2)[1];
	}
	

}
