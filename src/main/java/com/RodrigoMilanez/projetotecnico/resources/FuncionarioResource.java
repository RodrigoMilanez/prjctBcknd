package com.RodrigoMilanez.projetotecnico.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.RodrigoMilanez.projetotecnico.domain.Funcionario;
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
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Funcionario obj){
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
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		funSer.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Funcionario>> listarTodos() {
		
		List<Funcionario> obj = funSer.findAll();
		
		return ResponseEntity.ok().body(obj);
	}
}
