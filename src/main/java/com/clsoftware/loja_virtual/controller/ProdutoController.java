package com.clsoftware.loja_virtual.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.clsoftware.loja_virtual.model.Produto;
//import javax.validation.Valid;
import com.clsoftware.loja_virtual.repository.ProdutoRepository;

@Controller
public class ProdutoController {

	@Autowired
	ProdutoRepository produtoRepository;

	@RequestMapping("administrativo/produtos/cadastrar")
	public ModelAndView cadastrar(Produto produto) {
		ModelAndView andView = new ModelAndView("administrativo/produtos/cadastro");
		andView.addObject("produto", produto);

		return andView;
	}

	// teste de metodo salvando img no banco
	@RequestMapping(method = RequestMethod.POST, value = "**/produtos/salvar")
	public ModelAndView salvar(@Validated Produto produto, BindingResult result,
			@RequestParam("file") MultipartFile file) {

		if (result.hasErrors()) {
			return cadastrar(produto);
		}
		try { // fazer upLoad de img

			if (file.getSize() >0) {

				produto.setImagem(file.getBytes());
				produto.setNomeImagem(file.getOriginalFilename());

			} else {
				if (produto.getId() != null && produto.getId() > 0) { // p/editar arquivo
					Produto produtoTemp = produtoRepository.findById(produto.getId()).get();
					
					produto.setImagem(produtoTemp.getImagem());
					produto.setNomeImagem(produtoTemp.getNomeImagem());

				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		produtoRepository.saveAndFlush(produto);

		return cadastrar(new Produto());

	}

	@GetMapping("/administrativo/produtos/editar/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {

		Optional<Produto> produto = produtoRepository.findById(id);
		return cadastrar(produto.get());
	}

	@GetMapping("/administrativo/produtos/remover/{id}")
	public ModelAndView remover(@PathVariable("id") long id) {

		Optional<Produto> produto = produtoRepository.findById(id);
		produtoRepository.delete(produto.get());
		return listar();
	}

	@GetMapping("/administrativo/produtos/listar")
	public ModelAndView listar() {
		ModelAndView andView = new ModelAndView("administrativo/produtos/lista");
		andView.addObject("listaProdutos", produtoRepository.findAll());
		return andView;
	}

	@ResponseBody
	@GetMapping("/administrativo/produtos/mostrarImagem/{idProduto}")
	public byte[] retornarImagem(@PathVariable("idProduto") Long id) {
		Produto produto = produtoRepository.getOne(id);

		System.out.println("imagem "+produto.getImagem());
		return produto.getImagem();

	}

}
