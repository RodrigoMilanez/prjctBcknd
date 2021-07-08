package com.RodrigoMilanez.projetotecnico.domain.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;


import org.hibernate.validator.constraints.Length;

import com.RodrigoMilanez.projetotecnico.domain.Funcionario;
import com.RodrigoMilanez.projetotecnico.domain.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class NewFuncionarioDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;

	@NotEmpty(message="Este campo deve ser preenchido")
	@Length(min=5, max=80, message="O tamanho deve ter de 5 a 80 caracteres")
	private String nome;
	
	@JsonIgnore
	@NotEmpty(message="Este campo deve ser preenchido")
	private String email;
	
	private Perfil perfil;
	
	@JsonIgnore
	private String telefone;
	
	
	
	public NewFuncionarioDTO() {
		
	}
	
	public NewFuncionarioDTO(Funcionario obj) {
		
		this.id = obj.getId();
		this.nome=obj.getNome();
		this.telefone = obj.getTelefone();
		this.email=(obj.getEmail());
		this.perfil=obj.getPerfil();
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

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	
 }
