package com.RodrigoMilanez.projetotecnico;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

@SpringBootApplication
public class ProjetoTecnicoApplication implements CommandLineRunner{

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
	
	public static void main(String[] args) {
		SpringApplication.run(ProjetoTecnicoApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		//instanciando clientes
		Cliente cli1= new Cliente(null, "Mario Jorge", "marioJambalaya@gmail.com", "99998888", "Jambalaya Ocean Drive");
		Cliente cli2= new Cliente(null, "Augusto Carrara", "taxiscarrara@gmail.com", "88887777", "zona norte");
		//adicionando clientes a uma lista
		List<Cliente> lista = new ArrayList<>();
		lista.add(cli1);
		lista.add(cli2);
	
		Equipamento eq1= new Equipamento(null, "Betoneira 400 litros", "Construção", "Maqtron", "Motor principal não está girando");
		Equipamento eq2= new Equipamento(null, "Lava Louças", "Eletrodomésticos", "Brastemp", "não sai água");
		
		
		
		List<Equipamento> listaEq= new ArrayList<>();
		
		listaEq.add(eq1);
		listaEq.add(eq2);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		OrdemDeServico ods1 = new OrdemDeServico(null, sdf.parse("02/07/2021 09:13"), cli1, 145.00);
		
		ods1.getEquipamentos().addAll(Arrays.asList(eq1, eq2));
	
		cli1.getOrdens().addAll(Arrays.asList(ods1));
		
		List<OrdemDeServico> listaOds= new ArrayList<>();
		listaOds.add(ods1);
		
		eq1.setOrdem(ods1);
		eq2.setOrdem(ods1);
		
		Pagamento pgto1= new PagamentoComCartão(null, EstadoPagamento.PENDENTE, ods1, 8);
		ods1.setPagamento(pgto1);
		
		Funcionario f1 = new Funcionario(null, "Pedrinho", "88889999", "Drex@gaymer.com", Perfil.ADMIN);
		Funcionario f2 = new Funcionario(null, "Moreira", "99998888", "Marcelo@weeb.com", Perfil.ATENDENTE);
		Funcionario f3 = new Funcionario(null, "Paulo", "88889999", "paulao@regulagem.com", Perfil.TECNICO);

		funRep.saveAll(Arrays.asList(f1, f2, f3));
		odsRep.saveAll(Arrays.asList(ods1));		
		cliRep.saveAll(Arrays.asList(cli1,cli2));
		equiRep.saveAll(Arrays.asList(eq1,eq2));
		pagRep.saveAll(Arrays.asList(pgto1));
	}

}
