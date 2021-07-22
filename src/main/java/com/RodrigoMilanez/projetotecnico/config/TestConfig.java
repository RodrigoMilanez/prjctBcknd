package com.RodrigoMilanez.projetotecnico.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.RodrigoMilanez.projetotecnico.services.DBService;
import com.RodrigoMilanez.projetotecnico.services.EmailService;
import com.RodrigoMilanez.projetotecnico.services.MockMailService;

@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	private DBService dbSer;
	
	
	@Bean
	public boolean instantiateDatabase() {
		dbSer.instantiateDatabase();
		return true;
	}
	
	@Bean
	public EmailService emailService(){
		return new MockMailService();
		 
	}
	
}
