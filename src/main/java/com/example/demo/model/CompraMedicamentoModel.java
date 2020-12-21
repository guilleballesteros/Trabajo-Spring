package com.example.demo.model;

public class CompraMedicamentoModel {

	private int idMedicamento;
	private int idCompra;
	public CompraMedicamentoModel() {
		super();
	}
	public CompraMedicamentoModel(int idMedicamento, int idCompra) {
		super();
		this.idMedicamento = idMedicamento;
		this.idCompra = idCompra;
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
