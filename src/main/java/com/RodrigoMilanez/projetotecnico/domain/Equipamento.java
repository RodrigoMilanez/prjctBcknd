package com.RodrigoMilanez.projetotecnico.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Equipamento implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String nome;
	private String tipo;
	private String marca;
	private String avaria;
	
	@OneToOne( mappedBy = "equip" ,cascade=CascadeType.ALL)
	private OrdemDeServico ordem;
	
	public Equipamento() {
		
	}

	public Equipamento(Integer id, String nome, String tipo, String marca, String avaria) {
		super();
		this.id = id;
		this.nome = nome;
		this.tipo = tipo;
		this.marca = marca;
		this.avaria = avaria;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getAvaria() {
		return avaria;
	}

	public void setAvaria(String avaria) {
		this.avaria = avaria;
	}
	
	
	
}
