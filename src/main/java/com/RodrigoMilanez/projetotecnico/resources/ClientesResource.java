package com.RodrigoMilanez.projetotecnico.resources;

import java.net.URI;

import javax.transaction.Transactional;
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

import com.RodrigoMilanez.projetotecnico.domain.Cliente;
import com.RodrigoMilanez.projetotecnico.domain.dto.ClienteDTO;
import com.RodrigoMilanez.projetotecnico.domain.dto.ClienteNewDto;
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
	
	@PreAuthorize("hasAnyRole('ADMIN','ATENDENTE')")
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Cliente obj, @PathVariable Integer id) {
		obj.setId(id);
		obj = cliSer.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','ATENDENTE')")
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		cliSer.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> listarTodos(
			@RequestParam(value = "page" , defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage" , defaultValue = "24")Integer linesPerPage,
			@RequestParam(value = "orderBy" , defaultValue = "nome")String orderBy, 
			@RequestParam(value = "direction" , defaultValue = "ASC")String direction) {
		Page<Cliente> list = cliSer.findPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> listDTO = list.map(obj -> new ClienteDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','ATENDENTE')")
	@Transactional
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDto objDto){
		Cliente obj = cliSer.fromDTO(objDto);
		obj = cliSer.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
}
