package com.clsoftware.loja_virtual.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "produtos")
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;

	public Produto() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String descricao;	
	private String detalhesProduto;
	private String categoria;
	private String marca;
	private Double valorVenda=0.;
	private Double valorCusto=0.;
	private Double quantidadeEstoque =0.;
	private String nomeImagem;
	//@Lob
	private byte[] imagem;
	
}
