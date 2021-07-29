package com.RodrigoMilanez.projetotecnico.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.RodrigoMilanez.projetotecnico.domain.Funcionario;
import com.RodrigoMilanez.projetotecnico.domain.dto.FuncionarioDTO;
import com.RodrigoMilanez.projetotecnico.domain.dto.NewFuncionarioDTO;
import com.RodrigoMilanez.projetotecnico.services.FuncionarioService;

@RestController
@RequestMapping(value="/funcionarios")
public class FuncionarioResource {

	@Autowired
	private FuncionarioService funSer;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Funcionario> listar(@PathVariable Integer id) {
		
		Funcionario obj = funSer.findById(id);
		
		return ResponseEntity.ok().body(obj);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody NewFuncionarioDTO objDto){
		Funcionario obj = funSer.fromDTO(objDto);
		obj = funSer.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Funcionario obj, @PathVariable Integer id) {
		obj.setId(id);
		obj = funSer.update(obj);
		return ResponseEntity.noContent().build();
	}
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		funSer.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/email", method=RequestMethod.GET)
	public ResponseEntity<Funcionario> findByEmail(@RequestParam(value="value") String email) {
		Funcionario obj = funSer.findByEmail(email);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<FuncionarioDTO>> listarTodos(
			@RequestParam(value = "page" , defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage" , defaultValue = "24")Integer linesPerPage,
			@RequestParam(value = "orderBy" , defaultValue = "id")String orderBy, 
			@RequestParam(value = "direction" , defaultValue = "ASC")String direction) {
		Page<Funcionario> list = funSer.findPage(page, linesPerPage, orderBy, direction);
		Page<FuncionarioDTO> listDTO = list.map(obj -> new FuncionarioDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}
}
