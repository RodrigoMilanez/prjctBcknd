package com.RodrigoMilanez.projetotecnico.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.RodrigoMilanez.projetotecnico.domain.Cliente;
import com.RodrigoMilanez.projetotecnico.domain.Equipamento;
import com.RodrigoMilanez.projetotecnico.domain.Funcionario;
import com.RodrigoMilanez.projetotecnico.domain.OrdemDeServico;
import com.RodrigoMilanez.projetotecnico.domain.Pagamento;
import com.RodrigoMilanez.projetotecnico.domain.PagamentoComCartão;
import com.RodrigoMilanez.projetotecnico.domain.enums.EstadoPagamento;
import com.RodrigoMilanez.projetotecnico.domain.enums.Perfil;
import com.RodrigoMilanez.projetotecnico.repository.ClientesRepository;
import com.RodrigoMilanez.projetotecnico.repository.EquipamentosRepository;
import com.RodrigoMilanez.projetotecnico.repository.FuncionarioRepository;
import com.RodrigoMilanez.projetotecnico.repository.OrdensDeServicoRepository;
import com.RodrigoMilanez.projetotecnico.repository.PagamentoRepository;

@Service
public class DBService {

	@Autowired
	private ClientesRepository cliRep;

	@Autowired
	private EquipamentosRepository equiRep;

	@Autowired
	private OrdensDeServicoRepository odsRep;

	@Autowired
	private FuncionarioRepository funRep;

	@Autowired
	private PagamentoRepository pagRep;
	
	@Autowired
	private BCryptPasswordEncoder pe;

	public void instantiateDatabase() {

		// instanciando clientes
		Cliente cli1 = new Cliente(null, "Mario Jorge", "marioJambalaya@gmail.com", "99998888", "Jambalaya Ocean Drive",
				"62091740004");
		Cliente cli2 = new Cliente(null, "Augusto Carrara", "taxiscarrara@gmail.com", "88887777", "zona norte",
				"24089330050");
		// adicionando clientes a uma lista
		List<Cliente> lista = new ArrayList<>();
		lista.add(cli1);
		lista.add(cli2);

		OrdemDeServico ods1 = new OrdemDeServico(cli1, null);

		Equipamento eq1 = new Equipamento(null, "Betoneira 400 litros", "Construção", "Maqtron",
				"Motor principal não está girando");
		Equipamento eq2 = new Equipamento(null, "Lava Louças", "Eletrodomésticos", "Brastemp", "não sai água");
		Equipamento eq3 = new Equipamento(null, "Computador Gaymer", "Computadores", "RedDragon", "Cheiro de queimado");

		List<Equipamento> listaEq = new ArrayList<>();

		listaEq.add(eq1);
		listaEq.add(eq2);
		listaEq.add(eq3);

		ods1.setEquipamentos(listaEq);

		cli1.getOrdens().addAll(Arrays.asList(ods1));

		List<OrdemDeServico> listaOds = new ArrayList<>();
		listaOds.add(ods1);

		eq1.setOrdem(ods1);
		eq2.setOrdem(ods1);
		eq3.setOrdem(ods1);

		Pagamento pgto1 = new PagamentoComCartão(null, EstadoPagamento.PENDENTE, ods1, 8);
		ods1.setPagamento(pgto1);

		Funcionario f1 = new Funcionario(null, "Pedrinho", "88889999", "Drex@gaymer.com", Perfil.ADMIN, pe.encode("gamer"));
		Funcionario f2 = new Funcionario(null, "Moreira", "99998888", "Marcelo@weeb.com", Perfil.ATENDENTE, pe.encode("mamo42096"));
		Funcionario f3 = new Funcionario(null, "Paulo", "88889999", "paulao@regulagem.com", Perfil.TECNICO,pe.encode("bebel"));
		Funcionario f4 = new Funcionario(null, "Jaime", "78492154", "jaimeralperte@dndmf.com", Perfil.TECNICO,pe.encode("pamelamadindong"));
		Funcionario f5 = new Funcionario(null, "Angela", "84751865", "angelaquislene@dndmf.cm", Perfil.ATENDENTE,pe.encode("sprinkles"));
		Funcionario f6 = new Funcionario(null, "Maicon", "87579875", "maiconescort@dndmf.com", Perfil.TECNICO,pe.encode("thatswhatshesaid"));
		Funcionario f7 = new Funcionario(null, "Lesni", "88889999", "Lesni@parquesdp.com", Perfil.ATENDENTE,pe.encode("anne"));

		funRep.saveAll(Arrays.asList(f1, f2, f3, f4, f5, f6, f7));
		odsRep.saveAll(Arrays.asList(ods1));
		cliRep.saveAll(Arrays.asList(cli1, cli2));
		equiRep.saveAll(Arrays.asList(eq1, eq2, eq3));
		pagRep.saveAll(Arrays.asList(pgto1));
	}
}
