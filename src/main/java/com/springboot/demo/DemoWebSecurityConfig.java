package com.springboot.demo;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebMvcSecurity
/*@EnableWebSecurity*/
public class DemoWebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	DataSource dataSource;
	
	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	UserDetailsManager userDetailsManager() throws Exception{
		JdbcUserDetailsManager jdbcUserDetailsManager=new JdbcUserDetailsManager();
		jdbcUserDetailsManager.setAuthenticationManager(authenticationManagerBean());
		jdbcUserDetailsManager.setDataSource(dataSource);
		return jdbcUserDetailsManager;
	}
	
	
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	
	/* auth
     	.inMemoryAuthentication()
        .withUser("user").password("pass").roles("USER");*/
		
        auth
        	.userDetailsService(userDetailsManager())
        	.passwordEncoder(passwordEncoder());
        
        auth.jdbcAuthentication()
        	.dataSource(dataSource);
    }
	
	@Override
	public void configure(WebSecurity web) throws Exception{
		web
			.ignoring()
			.antMatchers("/css/**", "/images/**", "/js/**");
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception{
		
		http
			.authorizeRequests()
				.antMatchers("/", "/home")
				.permitAll()
				.antMatchers(HttpMethod.POST, "/login")
				.permitAll()
				.anyRequest()
				.authenticated()
				.and()
			.formLogin()
				.loginProcessingUrl("/login")
				.failureUrl("/login?error")
				.defaultSuccessUrl("/main")
				.loginPage("/login")
				.permitAll()
				.and()
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login?logout")
				.permitAll();

	}
	
}
