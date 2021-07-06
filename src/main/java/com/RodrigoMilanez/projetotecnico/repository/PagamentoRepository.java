package com.RodrigoMilanez.projetotecnico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.RodrigoMilanez.projetotecnico.domain.Pagamento;

@Repository
public interface PagamentoRepository extends  JpaRepository<Pagamento, Integer>{

}
