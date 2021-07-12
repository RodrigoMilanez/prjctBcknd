package com.RodrigoMilanez.projetotecnico.domain.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.RodrigoMilanez.projetotecnico.services.validation.ClienteInsert;

@ClienteInsert
public class ClienteNewDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message="Este campo deve ser preenchido")
	@Length(min=5, max=80, message="O tamanho deve ter de 5 a 80 caracteres")
	private String nome;
	@NotEmpty(message="Este campo deve ser preenchido")
	@Email(message="Email invalido")
	private String email;
	@NotEmpty(message="Este campo deve ser preenchido")
	private String telefone;
	@NotEmpty(message="Este campo deve ser preenchido")
	private String endereço;
	@NotEmpty(message="Este campo deve ser preenchido")
	private String cpf;
	
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


	public String getCpf() {
		return cpf;
	}


	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	
}
