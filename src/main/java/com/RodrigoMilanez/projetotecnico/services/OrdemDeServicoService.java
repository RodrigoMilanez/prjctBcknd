package com.RodrigoMilanez.projetotecnico.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.RodrigoMilanez.projetotecnico.domain.Cliente;
import com.RodrigoMilanez.projetotecnico.domain.Equipamento;
import com.RodrigoMilanez.projetotecnico.domain.OrdemDeServico;
import com.RodrigoMilanez.projetotecnico.domain.PagamentoComBoleto;
import com.RodrigoMilanez.projetotecnico.domain.dto.OrdemDeServiçoDTO;
import com.RodrigoMilanez.projetotecnico.domain.enums.EstadoPagamento;
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
}
