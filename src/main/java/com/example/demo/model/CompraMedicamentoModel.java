package com.example.demo.model;

import javax.persistence.EmbeddedId;



public class CompraMedicamentoModel {
	
	@EmbeddedId
	CompraMedicamentoKeyModel id;
	private int idMedicamento;
	private int idCompra;
	public CompraMedicamentoModel() {
		super();
	}
	public CompraMedicamentoModel(CompraMedicamentoKeyModel id ,int idMedicamento, int idCompra) {
		this.id=id;
		this.idMedicamento = idMedicamento;
		this.idCompra = idCompra;
	}
	
	public CompraMedicamentoKeyModel getId() {
		return id;
	}
	public void setId(CompraMedicamentoKeyModel id) {
		this.id = id;
	}
	public int getIdMedicamento() {
		return idMedicamento;
	}
	public void setIdMedicamento(int idMedicamento) {
		this.idMedicamento = idMedicamento;
	}
	public int getIdCompra() {
		return idCompra;
	}
	public void setIdCompra(int idCompra) {
		this.idCompra = idCompra;
	}
	
}
