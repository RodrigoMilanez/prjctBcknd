package com.RodrigoMilanez.projetotecnico.services;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
import com.RodrigoMilanez.projetotecnico.security.UserSS;
import com.RodrigoMilanez.projetotecnico.services.exceptions.AuthorizationException;
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
	private S3Service s3Ser;

	@Autowired
	private EquipamentoService eqSer;

	@Autowired
	private ImageService imgSer;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private EmailService emailSer;
	
	@Value("${img.prefix}")
	private String prefix;

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
		OrdemDeServico os = new OrdemDeServico(null,null, objDto.getPagamento());
		os.setCliente(cliente);
		os.setId(null);
		os.setEquipamentos(objDto.getEquipamentos());
		for (Equipamento i : objDto.getEquipamentos()) {
			i.setOrdem(os);
		}
		os.getPagamento().setEstado(EstadoPagamento.PENDENTE);
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
		if (newObj.getStatus().equals(Status.CANCELADO)) {
			newObj.getPagamento().setEstado(EstadoPagamento.CANCELADO);
		}
		return repo.save(newObj);
	}

	public OrdemDeServico concluir(Integer id, Status status) {
		OrdemDeServico newObj = findById(id);
		newObj.setStatus(status);
		if (newObj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagt = (PagamentoComBoleto) newObj.getPagamento();
			LocalDateTime instante = LocalDateTime.now();
			pagt.setDataPagamento(instante);
		}

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
		BigDecimal newOrcamento = ordem.getOrcamento();
		newOrcamento.subtract(eq.getOrcamento());
		try {
			if (eq.getOrdem().equals(ordem)) {
				eqRep.deleteById(eq.getId());
			}
			ordem.setStatus(Status.REPARO);
			ordem.setOrcamento(newOrcamento);
			repo.save(ordem);
		} catch (ObjectNotFoundException e) {
			throw new ObjectNotFoundException("Equipamento não existe");
		}
	}
	
	/*public void deletarEquipamento(Integer idEq) {
		Equipamento eq = eqSer.findById(idEq);
				eqRep.deleteById(eq.getId());
	}*/


	
	
	public OrdemDeServico orcamento(OrdemDeServico ods) {
		BigDecimal orcamentoODS = new BigDecimal(0);
		for (Equipamento eq : ods.getEquipamentos()) {
			if (eq != null)
			orcamentoODS = orcamentoODS.add(eq.getOrcamento());
		}
		ods.setOrcamento(orcamentoODS);
		return ods;
	}
	
	
	public URI uploadPicture(Integer id , Integer idEq,MultipartFile multipartFile) {	
		UserSS user = UserService.authenticaded();
		if (user == null) {
			throw new AuthorizationException("Você precisa estar logado para fazer upload de imagem!");
		}
		Equipamento eq = eqSer.findById(idEq);
		OrdemDeServico ordem = this.findById(id);
		BufferedImage jpgImage= imgSer.getJpgImageFromFile(multipartFile);
		String fileName= "ods" + id + "eq" + idEq + ".jpg";
		if (eq.getOrdem().equals(ordem)) {
			eq.setImg(fileName);
			eqRep.save(eq);
		}
		return s3Ser.uploadFile(imgSer.getInputStream(jpgImage,"jpg"), fileName , "image");
	}
	
	public Page<OrdemDeServico> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),
				orderBy);
		return repo.findAll(pageRequest);
	}
}
