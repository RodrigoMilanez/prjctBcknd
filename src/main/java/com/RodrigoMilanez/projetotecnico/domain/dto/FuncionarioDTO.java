package com.RodrigoMilanez.projetotecnico.domain.dto;

import java.io.Serializable;
import java.util.Set;

import com.RodrigoMilanez.projetotecnico.domain.Funcionario;
import com.RodrigoMilanez.projetotecnico.domain.enums.Perfil;
public class FuncionarioDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String nome;
	
	private Set<Perfil> perfil;
	
	public FuncionarioDTO() {
		
	}
	
	public FuncionarioDTO(Funcionario obj) {
		
		this.id = obj.getId();
		this.nome=obj.getNome();
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

	public Set<Perfil> getPerfil() {
		return perfil;
	}

	public void setPerfil(Set<Perfil> perfil) {
		this.perfil = perfil;
	}
	
 }
