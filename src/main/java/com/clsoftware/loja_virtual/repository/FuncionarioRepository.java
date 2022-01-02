package com.clsoftware.loja_virtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clsoftware.loja_virtual.model.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{

}
