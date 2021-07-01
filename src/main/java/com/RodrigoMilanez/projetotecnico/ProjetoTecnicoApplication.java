package com.RodrigoMilanez.projetotecnico;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.RodrigoMilanez.projetotecnico.domain.Clientes;
import com.RodrigoMilanez.projetotecnico.repository.ClientesRepository;

@SpringBootApplication
public class ProjetoTecnicoApplication implements CommandLineRunner{

	@Autowired
	private ClientesRepository cliRep;
	
	public static void main(String[] args) {
		SpringApplication.run(ProjetoTecnicoApplication.class, args);
		
		
		
	}

	@Override
	public void run(String... args) throws Exception {
		Clientes cli1= new Clientes(null, "Mario Jorge Dassoin", "marioDjambalaia@gmail.com", "99998888", "Jambalaya Ocean Drive");
		Clientes cli2= new Clientes(null, "Augusto Carrara", "taxiscarrara@gmail.com", "88887777", "zona norte");
		
		List<Clientes> lista = new ArrayList<>();
		lista.add(cli1);
		lista.add(cli2);
		
		cliRep.saveAll(Arrays.asList(cli1,cli2));
		
	}

}
