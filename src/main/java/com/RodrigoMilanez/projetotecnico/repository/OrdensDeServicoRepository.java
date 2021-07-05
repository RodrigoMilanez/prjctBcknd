package com.RodrigoMilanez.projetotecnico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.RodrigoMilanez.projetotecnico.domain.OrdemDeServico;
@Repository
public interface OrdensDeServicoRepository extends JpaRepository<OrdemDeServico, Integer> {

}
