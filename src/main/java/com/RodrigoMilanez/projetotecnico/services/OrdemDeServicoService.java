package com.RodrigoMilanez.projetotecnico.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.RodrigoMilanez.projetotecnico.domain.Cliente;
import com.RodrigoMilanez.projetotecnico.domain.Equipamento;
import com.RodrigoMilanez.projetotecnico.domain.OrdemDeServico;
import com.RodrigoMilanez.projetotecnico.domain.PagamentoComBoleto;
import com.RodrigoMilanez.projetotecnico.domain.dto.OrdemDeServiçoDTO;
import com.RodrigoMilanez.projetotecnico.domain.enums.EstadoPagamento;
import com.RodrigoMilanez.projetotecnico.domain.enums.Status;
import com.RodrigoMilanez.projetotecnico.repository.ClientesRepository;
import com.RodrigoMilanez.projetotecnico.repository.EquipamentosRepository;
import com.RodrigoMilanez.projetotecnico.repository.OrdensDeServicoRepository;
import com.RodrigoMilanez.projetotecnico.repository.PagamentoRepository;

@Service
public class OrdemDeServicoService {

	@Autowired
	private OrdensDeServicoRepository repo;
	
	@Autowired
	private PagamentoRepository pagRepo;
	
	@Autowired
	private ClientesRepository cliRep;
	
	@Autowired
	private EquipamentosRepository eqRep;
	
	@Autowired
	private ClientesService cliSer;
	
	@Autowired
	private BoletoService boletoService;

	public OrdemDeServico findById(Integer id) {
		Optional<OrdemDeServico> obj = repo.findById(id);
		return obj.orElse(null);
	}
	
	
	public OrdemDeServico fromDTO(OrdemDeServiçoDTO objDto) {
		Cliente cliente = cliSer.findById(objDto.getClienteId());
		cliRep.save(cliente);
		OrdemDeServico os= new OrdemDeServico(cliente, objDto.getPagamento());
		os.setEquipamentos(objDto.getEquipamentos());
		for (Equipamento i : objDto.getEquipamentos()) {
			i.setOrdem(os);
		}
		os.getPagamento().setEstado(EstadoPagamento.PENDENTE);;
		os.getPagamento().setOds(os);
		if (os.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagt= (PagamentoComBoleto) os.getPagamento();
			boletoService.preencherPagamento(pagt, new Date(System.currentTimeMillis()));
		}
		pagRepo.save(os.getPagamento());
		eqRep.saveAll(os.getEquipamentos());
		return os;
	}
	
	public OrdemDeServico insert(OrdemDeServiçoDTO objDto) {
		OrdemDeServico os = fromDTO(objDto);
		return repo.save(os);
	}
	
	
	
	/*else if (newObj.getStatus().equals(Status.REPARO)){
	newObj.setStatus(Status.CONCLUÍDO);	
	newObj.getPagamento().setEstado(EstadoPagamento.QUITADO);
}	*/
	
	public OrdemDeServico respostaCliente(Integer id, Status status) {
		OrdemDeServico newObj= findById(id);
		newObj.setStatus(status);
		if (newObj.getStatus().equals(Status.RECUSADO)) {
			newObj.getPagamento().setEstado(EstadoPagamento.CANCELADO);
		}
		return newObj;
	}
	
	
	public OrdemDeServico update(OrdemDeServico obj) {
		OrdemDeServico newObj = findById(obj.getId());
		updateData(newObj,obj);
		return repo.save(obj);
	}
	
	
	public OrdemDeServico updateStatus(OrdemDeServico obj) {
		OrdemDeServico newObj = findById(obj.getId());
		newObj.setStatus(Status.AGUARDANDO_CLIENTE);
		updateData(newObj, obj);
		obj.setStatus(newObj.getStatus());
		
		return repo.save(obj);		
	}
	
	
	public void updateData(OrdemDeServico newObj, OrdemDeServico obj) {
		
		
		for (Equipamento eq: obj.getEquipamentos()) {
			for (Equipamento newEq: newObj.getEquipamentos()) {
				if (eq.getId() == newEq.getId()) {
					newEq.setNome(eq.getNome());
					newEq.setAvaria(eq.getAvaria());	
					newEq.setOrdem(obj);
					eqRep.save(newEq);
				}	
			}
		}
		
		
		/*for (Equipamento eqObj: obj.getEquipamentos()) {
			for (Equipamento eqNewObj: newObj.getEquipamentos()) {
				if(eqObj.getId() == eqNewObj.getId()) {
					eqObj.setNome(eqNewObj.getNome());
				}
			}
		}*/
		
		/*newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());*/
	}
}
