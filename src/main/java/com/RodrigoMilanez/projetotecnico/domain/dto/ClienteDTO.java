package com.RodrigoMilanez.projetotecnico.domain.dto;

import java.io.Serializable;

import com.RodrigoMilanez.projetotecnico.domain.Cliente;
import com.RodrigoMilanez.projetotecnico.services.validation.ClienteUpdate;

@ClienteUpdate
public class ClienteDTO implements Serializable{

	
	private static final long serialVersionUID = 1L;

	private Integer id;
		
	private String nome;
	private String email; 
	
	public ClienteDTO() {
		
	}
	
	public ClienteDTO (Cliente obj) {
		id= obj.getId();
		nome= obj.getNome();
		email= obj.getEmail();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
