package com.RodrigoMilanez.projetotecnico.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.RodrigoMilanez.projetotecnico.domain.enums.EstadoPagamento;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pagamento implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer id;
	private Integer estado;
	
	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "pedido_id")
	@MapsId
	private OrdemDeServico ods;
	
	public Pagamento() {
		
	}

	public Pagamento(Integer id, EstadoPagamento estado, OrdemDeServico ods) {
		super();
		this.id = id;
		this.estado = estado.getCod();
		this.ods = ods;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public EstadoPagamento getEstado() {
		return EstadoPagamento.toEnum(estado);
	}

	public void setEstado(EstadoPagamento estado) {
		this.estado = estado.getCod();
	}

	public OrdemDeServico getOds() {
		return ods;
	}

	public void setOds(OrdemDeServico ods) {
		this.ods = ods;
	}
	
	
}
