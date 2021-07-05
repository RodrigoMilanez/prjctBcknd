package com.RodrigoMilanez.projetotecnico.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.RodrigoMilanez.projetotecnico.domain.OrdemDeServico;
import com.RodrigoMilanez.projetotecnico.services.OrdemDeServicoService;

@RestController
@RequestMapping(value="/ordens")
public class OrdemDeServicoResource {

	@Autowired
	private OrdemDeServicoService odsSer;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> listar(@PathVariable Integer id) {
		/**/
		
		OrdemDeServico obj = odsSer.findById(id);
		
		return ResponseEntity.ok().body(obj);
	}
}
