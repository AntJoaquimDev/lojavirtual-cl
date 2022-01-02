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
@Table(name = "itens_compra")
public class ItensCompra implements Serializable {

	private static final long serialVersionUID = 1L;

	public ItensCompra() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne	
	private Produto produto ;
	@ManyToOne	
	private Compra compra ;
	private Integer quantidade=0;
	private Double ValorUnitario =0.;
	private Double ValorTotal=0.;
	
}
