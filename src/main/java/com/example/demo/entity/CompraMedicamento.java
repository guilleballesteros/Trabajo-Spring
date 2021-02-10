package com.example.demo.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class CompraMedicamento {
	
	@EmbeddedId
	CompraMedicamentoKey id;
	@ManyToOne
	@MapsId("IdMedicamento")
	@JoinColumn(name="idMedicamento")
	private Medicamento medicamento;
	@ManyToOne
	@MapsId("IdCompra")
	@JoinColumn(name="idCompra")
	private Compra compra;
	
	public CompraMedicamento(CompraMedicamentoKey id, Medicamento medicamento, Compra compra) {
		this.id = id;
		this.medicamento = medicamento;
		this.compra = compra;
	}
	
	public CompraMedicamento() {}
	
	public CompraMedicamentoKey getId() {
		return id;
	}

	public void setId(CompraMedicamentoKey id) {
		this.id = id;
	}
	public Medicamento getMedicamento() {
		return medicamento;
	}
	public void setMedicamento(Medicamento medicamento) {
		this.medicamento = medicamento;
	}
	public Compra getCompra() {
		return compra;
	}
	public void setCompra(Compra compra) {
		this.compra = compra;
	}
	
}
