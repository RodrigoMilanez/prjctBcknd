package com.RodrigoMilanez.projetotecnico.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	private BigDecimal orcamento;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name= "ordem_id")
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

	public OrdemDeServico getOrdem() {
		return ordem;
	}

	public void setOrdem(OrdemDeServico ordem) {
		this.ordem = ordem;
	}

	public BigDecimal getOrcamento() {
		return orcamento;
	}

	public void setOrcamento(BigDecimal orçamento) {
		this.orcamento = orçamento;
	}

	
	
	@Override
	public String toString() {
		NumberFormat nfe = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));
		StringBuilder builder = new StringBuilder();
		builder.append("Equipamento [nome=");
		builder.append(nome);
		builder.append(", avaria=");
		builder.append(avaria);
		builder.append(", orcamento=");
		builder.append(nfe.format(orcamento));
		builder.append("]");
		return builder.toString();
	}
	
		
}
