package com.RodrigoMilanez.projetotecnico.domain.dto;

import java.io.Serializable;

import com.RodrigoMilanez.projetotecnico.domain.Cliente;
import com.RodrigoMilanez.projetotecnico.domain.OrdemDeServico;
import com.RodrigoMilanez.projetotecnico.domain.enums.Status;

public class OrdensDTO implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Cliente cliente;
	private Status status;
	
	public OrdensDTO() {
		
	}

	public OrdensDTO(OrdemDeServico ordem) {
		super();
		this.id = ordem.getId();
		this.cliente = ordem.getCliente();
		this.status = ordem.getStatus();
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}

	public Cliente getCliente() {
		return cliente;
	}



	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
}
