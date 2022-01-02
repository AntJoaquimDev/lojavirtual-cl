package com.clsoftware.loja_virtual.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.clsoftware.loja_virtual.model.Funcionario;
import com.clsoftware.loja_virtual.repository.CargoRepository;
import com.clsoftware.loja_virtual.repository.FuncionarioRepository;

@Controller
public class FuncionarioController {

	@Autowired
	FuncionarioRepository funcionarioRepository;
	@Autowired
	private CargoRepository cargoRepository;

	@RequestMapping("administrativo/funcionarios/cadastrar")
	public ModelAndView cadastrar(Funcionario funcionario) {
		ModelAndView andView = new ModelAndView("administrativo/funcionarios/cadastro");
		andView.addObject("listaCargos", cargoRepository.findAll());
		andView.addObject("funcionario", funcionario);
		return andView;
	}

	// @PostMapping("/administrativo/funcionarios/salvar")
	@RequestMapping(method = RequestMethod.POST, value = "**/funcionarios/salvar")
	public ModelAndView salvar(@Validated Funcionario funcionario, BindingResult result) {
		if (result.hasErrors()) {
			return cadastrar(funcionario);
		}
		funcionario.setSenha(new BCryptPasswordEncoder().encode(funcionario.getSenha()));
		
		//System.out.println(funcionario.getSenha());
		funcionarioRepository.saveAndFlush(funcionario);
		
		return cadastrar(new Funcionario());
	}

	@GetMapping("/administrativo/funcionarios/editar/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {
		
		Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
		return cadastrar(funcionario.get());
	}

	@GetMapping("/administrativo/funcionarios/remover/{id}")
	public ModelAndView remover(@PathVariable("id") long id) {

		Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
		funcionarioRepository.delete(funcionario.get());
		return listar();
	}

	@GetMapping("/administrativo/funcionarios/listar")
	public ModelAndView listar() {
		ModelAndView andView = new ModelAndView("administrativo/funcionarios/lista");
		andView.addObject("listaFuncionarios", funcionarioRepository.findAll());
		return andView;
	}
}
