package com.clsoftware.loja_virtual.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.clsoftware.loja_virtual.model.Papel;



@Controller
public class LoginController {

	@GetMapping("/login")
	public ModelAndView cadastrar(Papel papel) {
		ModelAndView mv =  new ModelAndView("login");
		
		return mv;
	}
	
	

}
