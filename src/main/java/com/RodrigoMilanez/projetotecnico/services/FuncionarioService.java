package com.RodrigoMilanez.projetotecnico.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.RodrigoMilanez.projetotecnico.domain.Funcionario;
import com.RodrigoMilanez.projetotecnico.repository.FuncionarioRepository;
import com.RodrigoMilanez.projetotecnico.services.exceptions.DataIntegrityException;
import com.RodrigoMilanez.projetotecnico.services.exceptions.ObjectNotFoundException;

@Service
public class FuncionarioService {

	@Autowired
	private FuncionarioRepository repo;
	
	public Funcionario findById(Integer id) {
		Optional<Funcionario> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Funcionario.class.getName()));
				
	
	}
	
	public Funcionario insert(Funcionario obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Funcionario update(Funcionario obj) {
		findById(obj.getId());
		return repo.save(obj);
	}
	
	public Void delete(Integer id) {
		findById(id);
		try {
		repo.deleteById(id);
		} catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("não é possível deletar o conteúdo");
		}
		return null;
	}
	
}
