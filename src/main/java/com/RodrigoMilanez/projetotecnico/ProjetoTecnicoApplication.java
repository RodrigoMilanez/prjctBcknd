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
import com.RodrigoMilanez.projetotecnico.domain.OrdemDeServico;
import com.RodrigoMilanez.projetotecnico.repository.ClientesRepository;
import com.RodrigoMilanez.projetotecnico.repository.EquipamentosRepository;
import com.RodrigoMilanez.projetotecnico.repository.OrdensDeServicoRepository;

@SpringBootApplication
public class ProjetoTecnicoApplication implements CommandLineRunner{

	@Autowired
	private ClientesRepository cliRep;
	
	@Autowired
	private EquipamentosRepository equiRep;
	
	@Autowired
	private OrdensDeServicoRepository odsRep;
	
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
		
		equiRep.saveAll(Arrays.asList(eq1,eq2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		OrdemDeServico ods1 = new OrdemDeServico(null, sdf.parse("02/07/2021 09:13"), cli1, eq1);
		
		cli1.getOrdens().addAll(Arrays.asList(ods1));
		
		List<OrdemDeServico> listaOds= new ArrayList<>();
		listaOds.add(ods1);
		
		odsRep.saveAll(Arrays.asList(ods1));		
		cliRep.saveAll(Arrays.asList(cli1,cli2));
	}

}
