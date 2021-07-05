package com.RodrigoMilanez.projetotecnico.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.RodrigoMilanez.projetotecnico.domain.OrdemDeServico;
import com.RodrigoMilanez.projetotecnico.repository.OrdensDeServicoRepository;

@Service
public class OrdemDeServicoService {

	@Autowired
	private OrdensDeServicoRepository repo;

	public OrdemDeServico findById(Integer id) {
		Optional<OrdemDeServico> obj = repo.findById(id);
		return obj.orElse(null);
	}

}
