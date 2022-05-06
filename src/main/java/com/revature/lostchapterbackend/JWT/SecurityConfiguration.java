package com.revature.lostchapterbackend.JWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.revature.lostchapterbackend.constants.SecurityConstant.*;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	private AuthorizationFilter authorizationFilter;
	private AccessDeniedHandler accessDeniedHandler;
	private AuthenticationEntryPoint authenticationEntryPoint;
	private UserDetailsService userDetailService;
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	public SecurityConfiguration(AuthorizationFilter authorizationFilter, AccessDeniedHandler accessDeniedHandler,
			AuthenticationEntryPoint authenticationEntryPoint, 
			@Qualifier("UserDetailService") UserDetailsService userDetailService,
			BCryptPasswordEncoder passwordEncoder) {

		this.authorizationFilter = authorizationFilter;
		this.accessDeniedHandler = accessDeniedHandler;
		this.authenticationEntryPoint = authenticationEntryPoint;
		this.userDetailService = userDetailService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder authentication) throws Exception {
		authentication.userDetailsService(userDetailService).passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeHttpRequests().antMatchers(PUBLIC_URLS).permitAll()
			.anyRequest().authenticated().and()
			.exceptionHandling().accessDeniedHandler(accessDeniedHandler)
			.authenticationEntryPoint(authenticationEntryPoint).and()
			.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception{
		return super.authenticationManagerBean();
	}
	
}
