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

import com.clsoftware.loja_virtual.model.Permissao;
import com.clsoftware.loja_virtual.repository.FuncionarioRepository;
import com.clsoftware.loja_virtual.repository.PapelRepository;
import com.clsoftware.loja_virtual.repository.PermissaoRepository;

@Controller
public class PermissaoController {
	
	@Autowired
	private PermissaoRepository permissaoRepository;

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private PapelRepository papelRepository;

	@GetMapping("/administrativo/permissoes/cadastrar")
	public ModelAndView cadastrar(Permissao permissao) {
		ModelAndView mv = new ModelAndView("administrativo/permissoes/cadastro");
		mv.addObject("permissao", permissao);
		mv.addObject("listaFuncionarios", funcionarioRepository.findAll());
		mv.addObject("listaPapeis", papelRepository.findAll());
		return mv;
	}

	@GetMapping("/administrativo/permissoes/listar")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("administrativo/permissoes/lista");
		mv.addObject("listaPemissoes", permissaoRepository.findAll());
		return mv;
	}

	@GetMapping("/administrativo/permissoes/editar/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {
		Optional<Permissao> permissao = permissaoRepository.findById(id);
		return cadastrar(permissao.get());
	}

	@GetMapping("/administrativo/permissoes/remover/{id}")
	public ModelAndView remover(@PathVariable("id") Long id) {
		Optional<Permissao> permissao = permissaoRepository.findById(id);
		permissaoRepository.delete(permissao.get());
		return listar();
	}

	@PostMapping("/administrativo/permissoes/salvar")
	public ModelAndView salvar(@Validated Permissao permissao, BindingResult result) {

		if (result.hasErrors()) {
			return cadastrar(permissao);
		}
		permissaoRepository.saveAndFlush(permissao);

		return cadastrar(new Permissao());
	}

}
