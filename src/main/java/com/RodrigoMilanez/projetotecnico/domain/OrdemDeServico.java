package com.RodrigoMilanez.projetotecnico.domain;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "ods")
	private Pagamento pagamento;
	
	 
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





	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	public Pagamento getPagamento() {
		return pagamento;
	}





	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}




	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrdemDeServico other = (OrdemDeServico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	
}
