package com.RodrigoMilanez.projetotecnico.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.RodrigoMilanez.projetotecnico.domain.Funcionario;
import com.RodrigoMilanez.projetotecnico.domain.dto.NewFuncionarioDTO;
import com.RodrigoMilanez.projetotecnico.domain.enums.Perfil;
import com.RodrigoMilanez.projetotecnico.repository.FuncionarioRepository;
import com.RodrigoMilanez.projetotecnico.security.UserSS;
import com.RodrigoMilanez.projetotecnico.services.exceptions.AuthorizationException;
import com.RodrigoMilanez.projetotecnico.services.exceptions.DataIntegrityException;
import com.RodrigoMilanez.projetotecnico.services.exceptions.ObjectNotFoundException;

@Service
public class FuncionarioService {

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private FuncionarioRepository repo;
	
	
	public Funcionario findById(Integer id) {
		UserSS user = UserService.authenticaded();
		
		if(user==null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
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
		updateData(newObj, obj);
		return repo.save(obj);
	}

	public Void delete(Integer id) {
		findById(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("não é possível deletar o conteúdo");
		}
		return null;
	}

	public Funcionario findByEmail(String email) {
		
		UserSS user = UserService.authenticaded();
		if (user == null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso negado");
		}
	
		Funcionario obj = repo.findByEmail(email);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + user.getId() + ", Tipo: " + Funcionario.class.getName());
		}
		return obj;
		
	}
	
	public List<Funcionario> findAll() {
		return repo.findAll();
	}

	public Page<Funcionario> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Funcionario fromDTO(NewFuncionarioDTO objDto) {
		Funcionario obj = new Funcionario(objDto.getId(), objDto.getNome(), objDto.getTelefone()
				, objDto.getEmail(),pe.encode(objDto.getSenha()));
		obj.addPerfil(objDto.getPerfil());
		return obj;
		
	}

	public void updateData(Funcionario newObj, Funcionario obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

}
