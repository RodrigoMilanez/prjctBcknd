package com.RodrigoMilanez.projetotecnico.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.RodrigoMilanez.projetotecnico.domain.Cliente;
import com.RodrigoMilanez.projetotecnico.domain.dto.ClienteDTO;
import com.RodrigoMilanez.projetotecnico.domain.dto.ClienteNewDto;
import com.RodrigoMilanez.projetotecnico.repository.ClientesRepository;
import com.RodrigoMilanez.projetotecnico.services.exceptions.ObjectNotFoundException;

@Service
public class ClientesService {

	@Autowired
	private ClientesRepository repo;
	
	public Cliente findById(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	public Void delete(Integer id) {
		findById(id);
		try {
		repo.deleteById(id);
		} catch(DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("não é possível deletar o conteúdo");
		}
		return null;
	}
	
	public List<Cliente> findAll(){
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),
				orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO  objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null,null); 
	}
	

	public Cliente update(Cliente obj) {
		Cliente newObj = findById(obj.getId());
		updateData(newObj,obj);
		return repo.save(newObj);
	}
	
	public void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
	public Cliente fromDTO(ClienteNewDto  objDto) {
		return new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getTelefone(), objDto.getEndereço(), objDto.getCpf()); 
	}
	
	
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	
}
