package com.RodrigoMilanez.projetotecnico.domain;



import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class OrdemDeServico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Date instante;
	
	@ManyToOne
	@JoinColumn(name ="cliente_id")
	@MapsId
	private Cliente cli;
	
	@OneToOne
	@JoinColumn(name = "equipamento_id")
	private Equipamento equip;
	
	private double orcamento;
	
	public OrdemDeServico() {
		
	}


	public OrdemDeServico(Integer id, Date instante ,Cliente cli, Equipamento equip) {
		super();
		this.id = id;
		this.instante = instante;
		this.cli = cli;
		this.equip = equip;
		this.setOrcamento(00.00);
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


	public Set<Cliente> getCli() {
		return new HashSet<Cliente>(id);
	}


	public void setCli(Cliente cli) {
		this.cli = cli;
	}
	
	
	
	
}
