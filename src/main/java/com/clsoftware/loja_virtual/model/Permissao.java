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
@Table(name = "permissoes")
public class Permissao implements Serializable {

	private static final long serialVersionUID = 1L;

	public Permissao() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Temporal(TemporalType.DATE)
	private Date dataCadastro = new Date();
	@ManyToOne
	private Funcionario funcionario;
	@ManyToOne
	private Papel papel ;
	
	
}
