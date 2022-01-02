package com.clsoftware.loja_virtual.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Data
@Table(name = "campra")
public class Compra implements Serializable {

	private static final long serialVersionUID = 1L;

	public Compra() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne	
	private Cliente Cliente ;	
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCompra = new Date();
	
	private String formaPagamento;
	private Double ValorTotal;
	
}
