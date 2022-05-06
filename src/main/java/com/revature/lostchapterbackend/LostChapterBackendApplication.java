package com.revature.lostchapterbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class LostChapterBackendApplication {

	public static void main(String[] args) {
		//this code runs the entire application, DO NOT DELETE!
		SpringApplication.run(LostChapterBackendApplication.class, args);   

	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}