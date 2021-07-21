package com.RodrigoMilanez.projetotecnico.services;

import org.springframework.mail.SimpleMailMessage;

import com.RodrigoMilanez.projetotecnico.domain.OrdemDeServico;

public interface EmailService {

	void sendOrderconfirmationemail (OrdemDeServico ods);
	
	void sendEmail(SimpleMailMessage msg);
}
