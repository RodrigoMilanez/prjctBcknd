package com.RodrigoMilanez.projetotecnico.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

public class OrdemDeServico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
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


	public OrdemDeServico(Integer id, Cliente cli, Equipamento equip) {
		super();
		this.id = id;
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
	
	
	
	
}
