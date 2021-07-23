package com.RodrigoMilanez.projetotecnico.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

import com.RodrigoMilanez.projetotecnico.domain.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class OrdemDeServico implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime instante = LocalDateTime.now();

	private Status status;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	@MapsId
	private Cliente cliente;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "ods")
	private Pagamento pagamento;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "ordem")
	private List<Equipamento> equipamentos = new ArrayList<>();

	private BigDecimal orcamento;

	public OrdemDeServico() {
	}

	public OrdemDeServico(Cliente cli, Pagamento pagamento) {
		super();
		this.cliente = cli;
		this.orcamento = new BigDecimal("0.0");
		this.pagamento = pagamento;
		this.status = Status.DIAGNÓSTICO;
	}

	public BigDecimal getOrcamento() {
		return orcamento;
	}

	public void setOrcamento(BigDecimal orcamento) {
		this.orcamento = orcamento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cli) {
		this.cliente = cli;
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

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public LocalDateTime getInstante() {
		return instante;
	}

	public void setInstante(LocalDateTime instante) {
		this.instante = instante;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
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

	
	// == fazer o to string da ordem, para mandar por email. Terminar o email.//
	@Override
	public String toString() {
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY hh:MM");
		StringBuilder builder = new StringBuilder();
		builder.append(" Número da Ordem id=");
		builder.append(getId());
		builder.append(", instante=");
		builder.append(getInstante());
		builder.append(", pagamento=");
		builder.append(getPagamento().getId());
		builder.append(", equipamentos=");
		builder.append(getEquipamentos().toString());
		builder.append(", orcamento=");
		builder.append(nf.format(getOrcamento()));
		builder.append("]");
		return builder.toString();
	}

}
