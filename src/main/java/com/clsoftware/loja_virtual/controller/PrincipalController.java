package com.clsoftware.loja_virtual.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PrincipalController {

	@GetMapping("/administrativo")
	public String acessarHome() {
		return "administrativo/home";
	}
}





