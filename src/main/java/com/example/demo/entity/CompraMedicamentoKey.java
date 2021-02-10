package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CompraMedicamentoKey implements Serializable{
	
	@Column(name= "medicamento_id")
	int medicamentoId;
	
	@Column(name= "compra_id")
	int compraId;

}
