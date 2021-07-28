package com.RodrigoMilanez.projetotecnico.resources;

import java.net.URI;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.RodrigoMilanez.projetotecnico.domain.OrdemDeServico;
import com.RodrigoMilanez.projetotecnico.domain.dto.OrdemDeServiçoDTO;
import com.RodrigoMilanez.projetotecnico.domain.enums.Status;
import com.RodrigoMilanez.projetotecnico.services.OrdemDeServicoService;

@RestController
@RequestMapping(value = "/ordens")
public class OrdemDeServicoResource {

	@Autowired
	private OrdemDeServicoService odsSer;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<OrdemDeServico> listar(@PathVariable Integer id) {
		/**/

		OrdemDeServico obj = odsSer.findById(id);

		return ResponseEntity.ok().body(obj);
	}

	@PreAuthorize("hasAnyRole('ADMIN','ATENDENTE')")
	@Transactional
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody OrdemDeServiçoDTO objDTO) {
		OrdemDeServico obj = odsSer.insert(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PreAuthorize("hasAnyRole('ADMIN','TECNICO')")
	// atualiza o status
	@RequestMapping(value = "/{id}/diagnostico", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateStatus(@RequestBody OrdemDeServico obj, @PathVariable Integer id) {
		obj.setId(id);
		obj = odsSer.updateDiagnostico(obj);
		return ResponseEntity.noContent().build();
	}

	// == cliente decide se prossegue com a ordem ou se cancela
	@RequestMapping(value = "/{id}/aprovado", method = RequestMethod.GET)
	public ResponseEntity<OrdemDeServico> aprovar(@PathVariable Integer id, Status status) {
		OrdemDeServico obj = odsSer.respostaCliente(id, Status.REPARO);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(value = "/{id}/recusado", method = RequestMethod.GET)
	public ResponseEntity<OrdemDeServico> recusar(@PathVariable Integer id, Status status) {
		OrdemDeServico obj = odsSer.respostaCliente(id, Status.RECUSADO);
		return ResponseEntity.ok().body(obj);
	}

	// == depois ajustar para funcionar com emails
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		odsSer.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	// == conclui a ordem de serviço
	@RequestMapping(value = "/{id}/concluir", method = RequestMethod.GET)
	public ResponseEntity<OrdemDeServico> concluir(@PathVariable Integer id, Status status) {
		OrdemDeServico obj = odsSer.concluir(id, Status.CONCLUÍDO);
		return ResponseEntity.ok().body(obj);
	}
	// == deleta um equipamento da ordem de serviço
	@PreAuthorize("hasAnyRole('ADMIN','TECNICO')")
	@DeleteMapping("/{idOrdem}/equipamentos/{idEq}")
	public ResponseEntity<Void> deletaEquipamento(@PathVariable(name = "idOrdem") Integer idOrdem,
			@PathVariable(name = "idEq") Integer idEq) {
		odsSer.deletarEquipamento(idOrdem, idEq);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','TECNICO')")
	@RequestMapping(value="/{id}/pictures", method=RequestMethod.POST)
	public ResponseEntity<Void> uploadProfilePicture(@PathVariable Integer id,@RequestParam(name="file") MultipartFile file){
		URI uri= odsSer.uploadPicture(id,file);
		return ResponseEntity.created(uri).build();
	}
}
