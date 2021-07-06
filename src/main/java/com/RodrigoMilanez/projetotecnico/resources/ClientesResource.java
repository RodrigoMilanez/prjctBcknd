package com.RodrigoMilanez.projetotecnico.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.RodrigoMilanez.projetotecnico.domain.Cliente;
import com.RodrigoMilanez.projetotecnico.services.ClientesService;

@RestController
@RequestMapping(value="/clientes")
public class ClientesResource {

	@Autowired
	private ClientesService cliSer;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Cliente> listar(@PathVariable Integer id) {
		
		Cliente obj = cliSer.findById(id);
		
		return ResponseEntity.ok().body(obj);
	}
}
