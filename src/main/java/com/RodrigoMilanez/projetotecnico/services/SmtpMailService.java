 package com.RodrigoMilanez.projetotecnico.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.slf4j.LoggerFactory;


public class SmtpMailService extends AbstractEmailService{
	
	@Autowired
	private MailSender mailSender;
	
	@Autowired
	private JavaMailSender jms;
	
	private static Logger LOG = LoggerFactory.getLogger(MockMailService.class);
	
	@Override
	public void sendEmail(SimpleMailMessage msg)  {
		LOG.info("Simulando envio de mensagem");
		mailSender.send(msg);
		LOG.info("email enviado!");
		
	}


	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		LOG.info("Simulando envio de mensagem");
		jms.send(msg);
		LOG.info("email enviado!");
	}
}