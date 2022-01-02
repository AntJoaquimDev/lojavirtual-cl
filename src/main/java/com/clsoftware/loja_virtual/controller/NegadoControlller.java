package com.clsoftware.loja_virtual.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;





@Controller
public class NegadoControlller {

	@GetMapping("/negado")
	public ModelAndView negadoAdminitrativo() {
		ModelAndView mv =  new ModelAndView("/negado");
		
		return mv;
	}
	@GetMapping("/negadoCliente")
	public ModelAndView negadoCliente() {
		ModelAndView mv =  new ModelAndView("/negadoCliente");
		
		return mv;
	}
	

}
