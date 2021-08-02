package com.RodrigoMilanez.projetotecnico.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.RodrigoMilanez.projetotecnico.services.DBService;

@Configuration
@Profile("prod")
public class prodConfig {

	@Autowired
	private DBService DBS;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		DBS.instantiateDatabase();
		return true;
	}
}

