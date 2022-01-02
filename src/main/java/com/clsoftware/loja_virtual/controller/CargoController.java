package com.clsoftware.loja_virtual.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.clsoftware.loja_virtual.model.Cargo;
import com.clsoftware.loja_virtual.repository.CargoRepository;
import com.clsoftware.loja_virtual.repository.FuncionarioRepository;



@Controller
public class CargoController {
	
	@Autowired
	private CargoRepository cargoRepository;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	
	@GetMapping("administrativo/cargos/cadastrar")
	public ModelAndView cadastrarCargo(Cargo cargo) {
		ModelAndView mv =  new ModelAndView("administrativo/cargos/cadastro");
		//mv.addObject("listaFuncionarios", funcionarioRepository.findAll());
		mv.addObject("cargo",cargo);
		return mv;
	}
	
	@GetMapping("/administrativo/cargos/listar")
	public ModelAndView listarCargo() {
		ModelAndView mv=new ModelAndView("administrativo/cargos/lista");
		mv.addObject("listaFuncionarios", funcionarioRepository.findAll());
		mv.addObject("listaCargos", cargoRepository.findAll());
		return mv;
	}
	
	@GetMapping("/administrativo/cargos/editar/{id}")
	public ModelAndView editarCargo(@PathVariable("id") Long id) {
		Optional<Cargo> cargo = cargoRepository.findById(id);
		return cadastrarCargo(cargo.get());
	}
	
	@GetMapping("/administrativo/cargos/remover/{id}")
	public ModelAndView removerCargo(@PathVariable("id") Long id) {
		Optional<Cargo> cargo = cargoRepository.findById(id);
		cargoRepository.delete(cargo.get());
		return listarCargo();
	}
	
	@PostMapping("/administrativo/cargos/salvar")
	public ModelAndView salvar(@Validated Cargo cargo, BindingResult result) {
		
		if(result.hasErrors()) {
			return cadastrarCargo(cargo);
		}
		cargoRepository.saveAndFlush(cargo);
		
		return cadastrarCargo(new Cargo());
	}


}
