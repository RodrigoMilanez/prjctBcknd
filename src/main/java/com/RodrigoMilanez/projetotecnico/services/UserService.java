package com.RodrigoMilanez.projetotecnico.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.RodrigoMilanez.projetotecnico.security.UserSS;

public class UserService {

	public static UserSS authenticaded() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
		} catch(Exception e) {
			return null;
		}
		
		}
}
