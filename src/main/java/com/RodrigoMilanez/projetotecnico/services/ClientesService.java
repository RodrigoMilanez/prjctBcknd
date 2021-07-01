package com.RodrigoMilanez.projetotecnico.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.RodrigoMilanez.projetotecnico.domain.Clientes;
import com.RodrigoMilanez.projetotecnico.repository.ClientesRepository;

@Service
public class ClientesService {

	@Autowired
	private ClientesRepository repo;
	
	public Clientes findById(Integer id) {
		Optional<Clientes> obj = repo.findById(id);
		return obj.orElse(null);
	}
	
	
	
}
