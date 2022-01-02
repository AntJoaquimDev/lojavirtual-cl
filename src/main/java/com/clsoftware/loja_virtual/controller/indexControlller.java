package com.clsoftware.loja_virtual.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.clsoftware.loja_virtual.model.Papel;
import com.clsoftware.loja_virtual.model.Produto;
import com.clsoftware.loja_virtual.repository.ProdutoRepository;




@Controller
public class indexControlller {

	@Autowired
	ProdutoRepository produtoRepository;
	
	@RequestMapping("/")
	public ModelAndView cadastrar(Papel papel) {
		ModelAndView mv =  new ModelAndView("/index");
		mv.addObject("listaProdutos", produtoRepository.findAll());
		
		return mv;
	}
	
	@RequestMapping("/index")
	public String index() {
		
		
		return "index";
	}
	
}
