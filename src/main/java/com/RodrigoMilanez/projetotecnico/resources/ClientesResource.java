package com.RodrigoMilanez.projetotecnico.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.RodrigoMilanez.projetotecnico.domain.Clientes;
import com.RodrigoMilanez.projetotecnico.services.ClientesService;

@RestController
@RequestMapping(value="/clientes")
public class ClientesResource {

	@Autowired
	private ClientesService cliSer;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> listar(@PathVariable Integer id) {
		/*Clientes cli1= new Clientes(1, "Mario Jorge Dassoin", "marioDjambalaia@gmail.com", "99998888", "Jambalaya Ocean Drive");
		Clientes cli2= new Clientes(2, "Augusto Carrara", "taxiscarrara@gmail.com", "88887777", "zona norte");
		
		List<Clientes> lista = new ArrayList<>();
		lista.add(cli1);
		lista.add(cli2);*/
		
		Clientes obj = cliSer.findById(id);
		
		return ResponseEntity.ok().body(obj);
	}
}
