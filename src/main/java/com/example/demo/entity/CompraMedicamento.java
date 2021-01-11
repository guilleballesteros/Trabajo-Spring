package com.example.demo.entity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class CompraMedicamento {

	@JoinColumn(name="idMedicamento")
	private Medicamento medicamento;
	@ManyToOne
	@JoinColumn(name="idMedico")
	private Compra compra;
	public CompraMedicamento(Medicamento medicamento, Compra compra) {
		super();
		this.medicamento = medicamento;
		this.compra = compra;
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
