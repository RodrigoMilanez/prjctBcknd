package com.RodrigoMilanez.projetotecnico.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.RodrigoMilanez.projetotecnico.domain.Clientes;

@RestController
@RequestMapping(value="/clientes")
public class ClientesResource {

	@RequestMapping(method=RequestMethod.GET)
	public List<Clientes> listar() {
		Clientes cli1= new Clientes(1, "Mario Jorge Dassoin", "marioDjambalaia@gmail.com", "99998888", "Jambalaya Ocean Drive");
		Clientes cli2= new Clientes(2, "Augusto Carrara", "taxiscarrara@gmail.com", "88887777", "zona norte");
		
		List<Clientes> lista = new ArrayList<>();
		lista.add(cli1);
		lista.add(cli2);
		
		
		
		return lista;
	}
}
