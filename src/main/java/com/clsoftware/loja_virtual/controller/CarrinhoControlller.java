package com.clsoftware.loja_virtual.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.clsoftware.loja_virtual.model.Cliente;
import com.clsoftware.loja_virtual.model.Compra;
import com.clsoftware.loja_virtual.model.ItensCompra;
import com.clsoftware.loja_virtual.model.Papel;
import com.clsoftware.loja_virtual.model.Produto;
import com.clsoftware.loja_virtual.repository.ClienteRepository;
import com.clsoftware.loja_virtual.repository.CompraRepository;
import com.clsoftware.loja_virtual.repository.ItensCompraRepository;
import com.clsoftware.loja_virtual.repository.ProdutoRepository;

@Controller
public class CarrinhoControlller {

	private List<ItensCompra> itensCompra = new ArrayList<>();
	private Compra compra = new Compra();
	private Cliente cliente;

	private void calcularTotal() {
		compra.setValorTotal(0.);
		for (ItensCompra it : itensCompra) {
			compra.setValorTotal(compra.getValorTotal() + it.getValorTotal());
		}
	}

	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private CompraRepository compraRepository;

	@Autowired
	private ItensCompraRepository itensCompraRepository;

	@GetMapping("/cliente/carrinhoCompra")
	public ModelAndView cadastrar(Papel papel) {
		ModelAndView mv = new ModelAndView("/cliente/carrinhoCompra");
		calcularTotal();
		mv.addObject("compra", compra);	
		mv.addObject("listaItens", produtoRepository.findAll());
		mv.addObject("listaItens", itensCompra);
		return mv;
	}

	public void buscarUsuarioLogado() {
		Authentication authenticado = SecurityContextHolder.getContext().getAuthentication();
		if (!(authenticado instanceof AnonymousAuthenticationToken)) {
			String email = authenticado.getName();
			cliente = clienteRepository.buscarClienteEmail(email).get(0);
		}

	}

	@GetMapping("/cliente/finalizar")
	public ModelAndView finalizarCompra(Papel papel) {
		buscarUsuarioLogado();
		ModelAndView mv = new ModelAndView("/cliente/finalizar");
		calcularTotal();
		mv.addObject("compra", compra);
		mv.addObject("listaItens", itensCompra);
		mv.addObject("cliente", cliente);
		return mv;
	}

	@PostMapping("/finalizar/confirmar")
	public ModelAndView confirmarCompra(String formaPagamento) {
	    ModelAndView mv = new ModelAndView("cliente/mensagemFinalizou");
		
		mv.addObject("compra", compra);
		compra.setCliente(cliente);
		compra.setFormaPagamento(formaPagamento);
		compraRepository.saveAndFlush(compra);

		for (ItensCompra c : itensCompra) {
			c.setCompra(compra);
			itensCompraRepository.saveAndFlush(c);
		}
		itensCompra = new ArrayList<>();
		compra = new Compra();
		return mv;
	}

	@GetMapping("/cliente/adicionarCarrinho/{id}")
	public String adicionarCarrinho(@PathVariable Long id) {

		ModelAndView mv = new ModelAndView("/cliente/carrinhoCompra");

		Optional<Produto> prod = produtoRepository.findById(id);
		Produto produto = prod.get();
		ItensCompra item = new ItensCompra();

		int controle = 0;
		for (ItensCompra it : itensCompra) {
			if (it.getProduto().getId().equals(produto.getId())) {
				it.setQuantidade(it.getQuantidade() + 1);
				controle = 1;
				break;
			}
		}
		if (controle == 0) {

			item.setProduto(produto);
			item.setValorUnitario(produto.getValorVenda());
			item.setQuantidade(item.getQuantidade() + 1);
			item.setValorTotal(item.getValorTotal() + (item.getQuantidade() * item.getValorUnitario()));
			itensCompra.add(item);

		}
		mv.addObject("listaItens", itensCompra);
		return "redirect:/cliente/carrinhoCompra";
	}

	@GetMapping("/alterarQuantidade/{id}/{acao}")
	public String alterarQuantidade(@PathVariable Long id, @PathVariable Integer acao) {

		for (ItensCompra it : itensCompra) {
			if (it.getProduto().getId().equals(id)) {
				if (acao.equals(1)) {
					it.setQuantidade(it.getQuantidade() + 1);
					it.setValorTotal(0.);
					it.setValorTotal(it.getValorTotal() + (it.getQuantidade() * it.getValorUnitario()));
				} else if (acao.equals(0)) {
					it.setQuantidade(it.getQuantidade() - 1);
					it.setValorTotal(0.);
					it.setValorTotal(it.getValorTotal() + (it.getQuantidade() * it.getValorUnitario()));
				}
				break;
			}
		}
		return "redirect:/cliente/carrinhoCompra";

	}

	@GetMapping("/removerProduto/{id}")
	public String removerProdutoCarrinho(@PathVariable Long id) {

		for (ItensCompra it : itensCompra) {
			if (it.getProduto().getId().equals(id)) {
				itensCompra.remove(it);
			}

			break;
		}

		return "redirect:/cliente/carrinhoCompra";
	}

}
