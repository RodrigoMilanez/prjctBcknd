package com.RodrigoMilanez.projetotecnico.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.RodrigoMilanez.projetotecnico.domain.OrdemDeServico;

public abstract class AbstractEmailService implements EmailService{
	
	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void sendOrderconfirmationemail (OrdemDeServico obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(OrdemDeServico obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.toString());
		sm.setFrom(sender);
		sm.setSubject("Aguardando confirmação do pedido"); 
		sm.setSentDate(new Date());
		return sm;
	}
}
