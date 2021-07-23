package com.RodrigoMilanez.projetotecnico.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.RodrigoMilanez.projetotecnico.domain.Funcionario;
import com.RodrigoMilanez.projetotecnico.repository.FuncionarioRepository;
import com.RodrigoMilanez.projetotecnico.security.UserSS;


@Service
public class UserDetailServiceImpl implements UserDetailsService{

	@Autowired
	private FuncionarioRepository rep;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Funcionario fun= rep.findByEmail(email);
		if (fun == null ) {
			throw new UsernameNotFoundException(email);
		}
		return new UserSS(fun.getId(),fun.getEmail(),fun.getSenha(),fun.getPerfil());
	}

}
