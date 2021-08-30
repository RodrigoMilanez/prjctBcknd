package com.RodrigoMilanez.projetotecnico.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.RodrigoMilanez.projetotecnico.domain.Funcionario;
import com.RodrigoMilanez.projetotecnico.domain.OrdemDeServico;


public abstract class AbstractEmailService implements EmailService{
	
	@Autowired
	private TemplateEngine templateEng;
	
	@Autowired
	private JavaMailSender JMS;
	
	@Value("${default.sender}")
	private String sender;
	
	@Value("${default.url}")
	private String url;
	
	@Override
	public void sendOrderconfirmationemail (OrdemDeServico obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);
	}
	
	@Override
	public void sendOrderConclusionEmail (OrdemDeServico obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromConclusao(obj);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(OrdemDeServico obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());
		sm.setText(htmlFromTemplatePedido(obj));;
		sm.setFrom(sender);
		sm.setSubject("Aguardando confirmação de ordem"); 
		sm.setSentDate(new Date());
		return sm;
	}
	
	protected SimpleMailMessage prepareSimpleMailMessageFromConclusao(OrdemDeServico obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());
		sm.setText(htmlFromTemplateConclusao(obj));;
		sm.setFrom(sender);
		sm.setSubject("Ordem concluída"); 
		sm.setSentDate(new Date());
		return sm;
	}
	//método que gera o email a partir do pedido
	protected String htmlFromTemplatePedido(OrdemDeServico obj) {
		Context context = new Context();
		context.setVariable("pedido", obj);
		return templateEng.process("email/confirmacaoPedido", context);
 	}
	//método que gera o email a partir da conclusão
	protected String htmlFromTemplateConclusao(OrdemDeServico obj) {
		Context context = new Context();
		context.setVariable("pedido", obj);
		return templateEng.process("email/conclusao", context);
 	}
	
	@Override
	public void sendOrderconfirmationHtmlEmail(OrdemDeServico obj) {
		try {
			MimeMessage mm = prepareMimeMailMessageFromPedido(obj);
			sendHtmlEmail(mm);
		}catch (MessagingException e) {
			sendOrderconfirmationemail(obj);
		}
	}
	
	@Override
	public void	sendOrderConclusionHtmlEmail(OrdemDeServico obj) {
		try {
			MimeMessage mm = prepareMimeMailMessageFromConclusao(obj);
			sendHtmlEmail(mm);
		}catch (MessagingException e) {
			sendOrderconfirmationemail(obj);
		}
	}
	
	protected MimeMessage prepareMimeMailMessageFromConclusao(OrdemDeServico obj) throws MessagingException {
		MimeMessage mimeMessage = JMS.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		mmh.setTo(obj.getCliente().getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("Aguardando confirmação do pedido");
		mmh.setSentDate(new Date());
		mmh.setText(htmlFromTemplateConclusao(obj), true);
		return mimeMessage;
	}

	protected MimeMessage prepareMimeMailMessageFromPedido(OrdemDeServico obj) throws MessagingException {
		MimeMessage mimeMessage = JMS.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		mmh.setTo(obj.getCliente().getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("Aguardando confirmação do pedido");
		mmh.setSentDate(new Date());
		mmh.setText(htmlFromTemplatePedido(obj), true);
		
		return mimeMessage;
	}
	
	@Override
	public void sendNewPasswordEmail(Funcionario fun, String newPass) {
		SimpleMailMessage sm = prepareNewPasswordEmail(fun, newPass);
		sendEmail(sm);
	}

	
	protected SimpleMailMessage prepareNewPasswordEmail(Funcionario fun, String newPass) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(fun.getEmail());
		sm.setFrom(sender);
		sm.setSubject("Solicitação de nova senha");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("Nova senha: " + newPass);
		return sm;
	}
}
