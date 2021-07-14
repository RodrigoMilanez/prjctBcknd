package com.RodrigoMilanez.projetotecnico.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.RodrigoMilanez.projetotecnico.domain.enums.EstadoPagamento;
import com.fasterxml.jackson.annotation.JsonTypeName;

@Entity
@JsonTypeName("pagamentoComBoleto")
public class PagamentoComBoleto extends Pagamento{
	private static final long serialVersionUID = 1L;
	private Date dataVencimento;
	private Date dataPagamento;
	
	public PagamentoComBoleto() {
		
	}
	
	public PagamentoComBoleto(Integer id, EstadoPagamento estado, OrdemDeServico ods, Date dataVencimento, Date dataPagamento) {
		super(id, estado, ods);
		this.dataVencimento = dataVencimento;
		this.dataPagamento = dataPagamento;
	}



	public Date getDataVencimento() {
		return dataVencimento;
	}



	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}



	public Date getDataPagamento() {
		return dataPagamento;
	}



	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}



	
	
}
