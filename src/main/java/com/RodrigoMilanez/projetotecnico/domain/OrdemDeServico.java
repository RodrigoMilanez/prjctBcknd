package com.RodrigoMilanez.projetotecnico.domain;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class OrdemDeServico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Date instante;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name ="cliente_id")
	@MapsId
	private Cliente cli;
	
	
	 
	@OneToMany(mappedBy = "ordem")
	private List<Equipamento> equipamentos = new ArrayList<>();
	
	
	private double orcamento;
	
	public OrdemDeServico() {
		
	}


	 


	public OrdemDeServico(Integer id, Date instante, Cliente cli, double orcamento) {
		super();
		this.id = id;
		this.instante = instante;
		this.cli = cli;
		this.orcamento = orcamento;
	}





	public double getOrcamento() {
		return orcamento;
	}


	public void setOrcamento(double orcamento) {
		this.orcamento = orcamento;
	}


	public Date getInstante() {
		return instante;
	}


	public void setInstante(Date instante) {
		this.instante = instante;
	}


	public Cliente getCli() {
		return cli;
	}


	public void setCli(Cliente cli) {
		this.cli = cli;
	}





	public Integer getId() {
		return id;
	}





	public void setId(Integer id) {
		this.id = id;
	}





	public List<Equipamento> getEquipamentos() {
		return equipamentos;
	}





	public void setEquipamentos(List<Equipamento> equipamentos) {
		this.equipamentos = equipamentos;
	}


	
	
	
	
	
}
