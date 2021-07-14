package com.RodrigoMilanez.projetotecnico.services;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.RodrigoMilanez.projetotecnico.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	public void preencherPagamento(PagamentoComBoleto pagto, Date instanteDoPedido) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(instanteDoPedido);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pagto.setDataVencimento(cal.getTime());
	}
}
