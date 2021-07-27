package com.RodrigoMilanez.projetotecnico.domain.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.RodrigoMilanez.projetotecnico.domain.Equipamento;
import com.RodrigoMilanez.projetotecnico.domain.Pagamento;

public class OrdemDeServiçoDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer Id;
	
	private Integer clienteId;
	
	private List<Equipamento> equipamentos = new ArrayList<>();
	
	private Pagamento pagamento;

	public Integer getClienteId() {
		return clienteId;
	}

	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
	}

	public List<Equipamento> getEquipamentos() {
		return equipamentos;
	}

	public void setEquipamentos(List<Equipamento> equipamentos) {
		this.equipamentos = equipamentos;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	
}
