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

import com.clsoftware.loja_virtual.model.Cliente;
import com.clsoftware.loja_virtual.repository.ClienteRepository;

@Controller
public class ClienteController {

	@Autowired
	ClienteRepository clienteRepository;
	
	@GetMapping("/cliente/cadastrar")
	public ModelAndView cadastrar(Cliente cliente) {
		ModelAndView andView = new ModelAndView("cliente/cadastrar");
		andView.addObject("cliente", cliente);
		andView.addObject("listaClientes", clienteRepository.findAll());		
		return andView;
	}

	
	@RequestMapping(method = RequestMethod.POST, value = "**/cliente/salvar")
	public ModelAndView salvar(@Validated Cliente cliente, BindingResult result) {
		if (result.hasErrors()) {
			return cadastrar(cliente);
		}
		cliente.setSenha(new BCryptPasswordEncoder().encode(cliente.getSenha()));
		
		clienteRepository.saveAndFlush(cliente);
		
		return cadastrar(new Cliente());
	}

	@GetMapping("/cliente/editar/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {
		
		Optional<Cliente> cliente = clienteRepository.findById(id);
		return cadastrar(cliente.get());
	}
/*
	@GetMapping("/cliente/remover/{id}")
	public ModelAndView remover(@PathVariable("id") long id) {

		Optional<Cliente> cliente = clienteRepository.findById(id);
		clienteRepository.delete(cliente.get());
		return listar();
	}
*/
	@GetMapping("/cliente/listar")
	public ModelAndView listar() {
		ModelAndView andView = new ModelAndView("cliente/lista");
		andView.addObject("listaClientes", clienteRepository.findAll());
		return andView;
	}
	
}
