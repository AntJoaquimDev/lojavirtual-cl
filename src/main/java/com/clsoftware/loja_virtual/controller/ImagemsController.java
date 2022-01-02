package com.clsoftware.loja_virtual.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.clsoftware.loja_virtual.model.Produto;

import com.clsoftware.loja_virtual.repository.ProdutoRepository;

@Controller
public class ImagemsController {
		
	@Autowired
	ProdutoRepository produtoRepository;

	@ResponseBody
	@GetMapping("/mostrarImagem/{idProduto}")
	public byte[] retornarImagem(@PathVariable("idProduto") Long id) {
		Produto produto = produtoRepository.getOne(id);

		//System.out.println("imagem "+produto.getImagem());
		return produto.getImagem();

	}	  
	  
	
}
