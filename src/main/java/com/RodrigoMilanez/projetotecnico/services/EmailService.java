package com.RodrigoMilanez.projetotecnico.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.RodrigoMilanez.projetotecnico.domain.Funcionario;
import com.RodrigoMilanez.projetotecnico.domain.OrdemDeServico;

public interface EmailService {

	void sendOrderconfirmationemail (OrdemDeServico ods);
	
	void sendEmail(SimpleMailMessage msg);
	
	void sendOrderconfirmationHtmlEmail (OrdemDeServico ods);
	
	void sendHtmlEmail(MimeMessage msg);
	
	void sendNewPasswordEmail(Funcionario funs, String newPass);
}

