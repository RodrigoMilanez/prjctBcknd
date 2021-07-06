package com.RodrigoMilanez.projetotecnico.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.RodrigoMilanez.projetotecnico.domain.Equipamento;
import com.RodrigoMilanez.projetotecnico.services.EquipamentoService;

@RestController
@RequestMapping(value="/equipamentos")
public class EquipamentoResource {

	@Autowired
	private EquipamentoService odsSer;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Equipamento> listar(@PathVariable Integer id) {
		/**/
		
		Equipamento obj = odsSer.findById(id);
		
		return ResponseEntity.ok().body(obj);
	}
}
