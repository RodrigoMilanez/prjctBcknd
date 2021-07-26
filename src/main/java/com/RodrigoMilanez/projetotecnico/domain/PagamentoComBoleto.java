package com.RodrigoMilanez.projetotecnico.domain;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;

import com.RodrigoMilanez.projetotecnico.domain.enums.EstadoPagamento;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;

@Entity
@JsonTypeName("pagamentoComBoleto")
public class PagamentoComBoleto extends Pagamento{
	private static final long serialVersionUID = 1L;
	private Date dataVencimento;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dataPagamento = null;
	
	public PagamentoComBoleto() {
		
	}
	
	public PagamentoComBoleto(Integer id, EstadoPagamento estado, OrdemDeServico ods, Date dataVencimento) {
		super(id, estado, ods);
		this.dataVencimento = dataVencimento;
	}



	public Date getDataVencimento() {
		return dataVencimento;
	}



	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}



	public LocalDateTime getDataPagamento() {
		return dataPagamento;
	}



	public void setDataPagamento(LocalDateTime dataPagamento) {
		this.dataPagamento = dataPagamento;
	}



	
	
}
