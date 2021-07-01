package com.RodrigoMilanez.projetotecnico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.RodrigoMilanez.projetotecnico.domain.Cliente;

@Repository
public interface ClientesRepository extends  JpaRepository<Cliente, Integer>{

}
