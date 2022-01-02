package com.clsoftware.loja_virtual.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.clsoftware.loja_virtual.model.EntradaItens;
import com.clsoftware.loja_virtual.model.EntradaProduto;
import com.clsoftware.loja_virtual.model.Produto;
import com.clsoftware.loja_virtual.repository.EntradaItensRepository;
import com.clsoftware.loja_virtual.repository.EntradaProdutoRepository;
import com.clsoftware.loja_virtual.repository.FuncionarioRepository;
import com.clsoftware.loja_virtual.repository.ProdutoRepository;


@Controller
public class EntradaProdutoController {

	private List<EntradaItens> listaEntrada = new ArrayList<EntradaItens>();

	@Autowired
	private EntradaProdutoRepository entradaProdutoRepositorio;

	@Autowired
	private EntradaItensRepository entradaItensRepositorio;

	@Autowired
	private FuncionarioRepository funcionarioRepositorio;

	@Autowired
	private ProdutoRepository produtoRepositorio;

	@GetMapping("/administrativo/entrada/cadastrar")
	public ModelAndView cadastrar(EntradaProduto entrada, EntradaItens entradaItens) {
		ModelAndView mv = new ModelAndView("administrativo/entrada/cadastro");
		mv.addObject("entrada", entrada);
		mv.addObject("listaEntradaItens", this.listaEntrada);
		mv.addObject("entradaItens", entradaItens);
		mv.addObject("listaFuncionarios", funcionarioRepositorio.findAll());
		mv.addObject("listaProdutos", produtoRepositorio.findAll());
		return mv;
	}

	
	
	
	//@GetMapping("/administrativo/produtos/editar/{id}")
	/*public ModelAndView editar(@PathVariable("id") Long id) {
		
		Optional<Produto> produto = produtoRepository.findById(id);
		return cadastrar(produto.get());
	}
*/
	//@GetMapping("/administrativo/produtos/remover/{id}")
/*	public ModelAndView remover(@PathVariable("id") long id) {

		Optional<Produto> produto = produtoRepository.findById(id);
		produtoRepository.delete(produto.get());
		return listar();
	}
*/
	@GetMapping("/administrativo/enrtrada/listar")
	public ModelAndView listar() {
		ModelAndView andView = new ModelAndView("administrativo/entrada/lista");
		andView.addObject("listaentradaItens", entradaItensRepositorio.findAll());
		//System.out.println(andView);
		return andView;
	} 
	
	
	@PostMapping("/administrativo/entrada/salvar")
	public ModelAndView salvar(String acao, EntradaProduto entrada, EntradaItens entradaItens) {

		if (acao.equals("itens")) {
			this.listaEntrada.add(entradaItens);
		} else if (acao.equals("salvar")) {
			entradaProdutoRepositorio.saveAndFlush(entrada);
			for (EntradaItens item : listaEntrada) {
				item.setEntrada(entrada);
				entradaItensRepositorio.saveAndFlush(item);
				Optional<Produto> prod = produtoRepositorio.findById(item.getProduto().getId());
				Produto produto = prod.get();
				
				produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + item.getQuantidade());
				
				produto.setValorCusto(item.getValorCusto());
				produto.setValorVenda(item.getValorVenda());
				
				produtoRepositorio.saveAndFlush(produto);
				this.listaEntrada = new ArrayList<>(); //new list.
			}
			return cadastrar(new EntradaProduto(), new EntradaItens());
		}

		return cadastrar(entrada, new EntradaItens());
	}

}
