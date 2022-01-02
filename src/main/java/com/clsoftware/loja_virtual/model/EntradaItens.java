package com.clsoftware.loja_virtual.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "entrada_itens")
public class EntradaItens implements Serializable {

	private static final long serialVersionUID = 1L;

	public EntradaItens() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private EntradaProduto entrada;
	@ManyToOne
	private Produto produto;
	private double quantidade=0.;
	private Double valorCusto=0.;	
	private Double valorVenda=0.;
	
}
