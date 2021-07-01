package com.RodrigoMilanez.projetotecnico.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.RodrigoMilanez.projetotecnico.domain.Equipamento;
import com.RodrigoMilanez.projetotecnico.repository.EquipamentosRepository;

@Service
public class EquipamentosService {
	
	@Autowired
	private EquipamentosRepository repo;
	
	public Equipamento findById(Integer id) {
		Optional<Equipamento> obj = repo.findById(id);
		return obj.orElse(null);
	}
}
