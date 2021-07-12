package com.RodrigoMilanez.projetotecnico.domain.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class ClienteNewDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotEmpty
	private String nome;
	@NotEmpty
	@Email
	private String email;
	private String telefone;
	private String endereço;
	
	public ClienteNewDto() {
		
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
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getEndereço() {
		return endereço;
	}
	public void setEndereço(String endereço) {
		this.endereço = endereço;
	}
	
	
}
