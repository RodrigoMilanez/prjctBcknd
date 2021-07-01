package com.RodrigoMilanez.projetotecnico;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.RodrigoMilanez.projetotecnico.domain.Cliente;
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
		Cliente cli1= new Cliente(null, "Mario Jorge", "marioJambalaya@gmail.com", "99998888", "Jambalaya Ocean Drive");
		Cliente cli2= new Cliente(null, "Augusto Carrara", "taxiscarrara@gmail.com", "88887777", "zona norte");
		
		List<Cliente> lista = new ArrayList<>();
		lista.add(cli1);
		lista.add(cli2);
		
		cliRep.saveAll(Arrays.asList(cli1,cli2));
		
	}

}
