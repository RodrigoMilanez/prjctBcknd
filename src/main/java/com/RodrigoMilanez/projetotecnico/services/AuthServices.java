package com.RodrigoMilanez.projetotecnico.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.RodrigoMilanez.projetotecnico.domain.Funcionario;
import com.RodrigoMilanez.projetotecnico.repository.FuncionarioRepository;
import com.RodrigoMilanez.projetotecnico.services.exceptions.ObjectNotFoundException;

@Service
public class AuthServices {

	@Autowired
	private FuncionarioRepository funRep;
	
	@Autowired
	private BCryptPasswordEncoder pEn;
	
	@Autowired
	private EmailService emailSer;
	
	private Random rndm = new Random();
	
	public void sendNewPassword(String email) {
		
		Funcionario fun = funRep.findByEmail(email);
		if (fun == null) {
			throw new ObjectNotFoundException("email não encontrado");
		}
		String newPass= newPassword();
		fun.setSenha(pEn.encode(newPass));
		
		funRep.save(fun);
		
		emailSer.sendNewPasswordEmail(fun,newPass);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i=0; i<10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = rndm.nextInt();
		if (opt == 0) { // gera um digito
			return (char) (rndm.nextInt(10) + 48);
		}
		else if (opt == 1) { // gera letra maiuscula
			return (char) (rndm.nextInt(26) + 65);
		}
		else { // gera letra minuscula
			return (char) (rndm.nextInt(26) + 97);
		}
				
	}
}