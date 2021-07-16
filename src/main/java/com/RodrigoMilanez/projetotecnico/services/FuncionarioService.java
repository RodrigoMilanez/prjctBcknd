package com.RodrigoMilanez.projetotecnico.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.RodrigoMilanez.projetotecnico.domain.Funcionario;
import com.RodrigoMilanez.projetotecnico.domain.dto.NewFuncionarioDTO;
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
		Funcionario newObj = findById(obj.getId());
		updateData(newObj,obj);
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
	
	public List<Funcionario> findAll(){
		return repo.findAll();
	}
	
	public Page<Funcionario> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),
				orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Funcionario fromDTO(NewFuncionarioDTO  objDto) {
		return new Funcionario(objDto.getId(), objDto.getNome(), objDto.getTelefone(), objDto.getEmail(), objDto.getPerfil());
	}
	
	public void updateData(Funcionario newObj, Funcionario obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
	/*obj.getEquipamentos().forEach(eq -> {
		if (!newObj.getEquipamentos().contains(eq)) {
			eqRep.deleteById(eq.getId());
		}
	});
	newObj.setOrcamento(obj.getOrcamento());
	repo.save(newObj);*/
	
}
