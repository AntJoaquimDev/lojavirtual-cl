package com.clsoftware.loja_virtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.clsoftware.loja_virtual.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

	@Query("from Produto where categoria =?1")
	public List<Produto> buscarCategoria(String categoria);
}
