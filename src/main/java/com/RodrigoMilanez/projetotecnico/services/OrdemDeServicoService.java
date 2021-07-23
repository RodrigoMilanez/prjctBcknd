package com.RodrigoMilanez.projetotecnico.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
import com.RodrigoMilanez.projetotecnico.services.exceptions.DataIntegrityException;
import com.RodrigoMilanez.projetotecnico.services.exceptions.ObjectNotFoundException;

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
	private EquipamentoService eqSer;

	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private EmailService emailSer;

	public OrdemDeServico findById(Integer id) {
		Optional<OrdemDeServico> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + OrdemDeServico.class.getName()));
	}

	public Void delete(Integer id) {
		findById(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("não é possível deletar o conteúdo");
		}
		return null;
	}

	public OrdemDeServico fromDTO(OrdemDeServiçoDTO objDto) {
		Cliente cliente = cliSer.findById(objDto.getClienteId());
		cliRep.save(cliente);
		OrdemDeServico os = new OrdemDeServico(cliente, objDto.getPagamento());
		os.setId(null);
		os.setEquipamentos(objDto.getEquipamentos());
		for (Equipamento i : objDto.getEquipamentos()) {
			i.setOrdem(os);
		}
		os.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		;
		os.getPagamento().setOds(os);
		if (os.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagt = (PagamentoComBoleto) os.getPagamento();
			boletoService.preencherPagamento(pagt, new Date(System.currentTimeMillis()));
		}
		pagRepo.save(os.getPagamento());
		eqRep.saveAll(os.getEquipamentos());
		return os;
	}

	public OrdemDeServico insert(OrdemDeServiçoDTO objDto) {
		OrdemDeServico os = fromDTO(objDto);
		System.out.println(os);
		return repo.save(os);
	}

	public OrdemDeServico respostaCliente(Integer id, Status status) {
		OrdemDeServico newObj = findById(id);
		newObj.setStatus(status);
		if (newObj.getStatus().equals(Status.RECUSADO)) {
			newObj.getPagamento().setEstado(EstadoPagamento.CANCELADO);
		}
		return repo.save(newObj);
	}

	public OrdemDeServico concluir(Integer id, Status status) {
		OrdemDeServico newObj = findById(id);
		newObj.setStatus(status);

		newObj.getPagamento().setEstado(EstadoPagamento.QUITADO);

		return repo.save(newObj);
	}

	public OrdemDeServico updateDiagnostico(OrdemDeServico obj) {
		OrdemDeServico newObj = findById(obj.getId());
		newObj.setStatus(Status.AGUARDANDO_CLIENTE);
		updateData(obj, newObj);
		emailSer.sendOrderconfirmationemail(newObj);
		return repo.save(newObj);
	}

	public void updateData(OrdemDeServico obj, OrdemDeServico newObj) {
		BigDecimal orcamentoODS = new BigDecimal(0);
		for (Equipamento eq : obj.getEquipamentos()) {
			for (Equipamento newEq : newObj.getEquipamentos()) {
				if (eq.getId() == newEq.getId()) {
					newEq.setNome(eq.getNome());
					newEq.setAvaria(eq.getAvaria());
					newEq.setOrdem(obj);
					newEq.setOrcamento(eq.getOrcamento());
					eqRep.save(newEq);
					orcamentoODS = orcamentoODS.add(newEq.getOrcamento());
				}
			}
		}
		newObj.setOrcamento(orcamentoODS);
	}

	public void deletarEquipamento(Integer idOrdem, Integer idEq) {
		OrdemDeServico ordem = this.findById(idOrdem);
		Equipamento eq = eqSer.findById(idEq);
		try {
			if (eq.getOrdem().equals(ordem)) {
				eqRep.deleteById(eq.getId());
			}
			updateDiagnostico(ordem);
			ordem.setStatus(Status.REPARO);
			repo.save(ordem);
		} catch (ObjectNotFoundException e) {
			throw new ObjectNotFoundException("Equipamento não existe");
		}
	}
}
