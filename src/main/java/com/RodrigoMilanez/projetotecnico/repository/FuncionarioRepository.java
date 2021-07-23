package com.RodrigoMilanez.projetotecnico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.RodrigoMilanez.projetotecnico.domain.Funcionario;
@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {

	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	Funcionario findByEmail(String email);
}
