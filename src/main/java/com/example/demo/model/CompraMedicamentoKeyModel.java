package com.example.demo.model;


import javax.persistence.Embeddable;

@Embeddable
public class CompraMedicamentoKeyModel {
	
	
	int medicamentoId;
	
	int compraId;

	public CompraMedicamentoKeyModel(int medicamentoId, int compraId) {
		this.medicamentoId = medicamentoId;
		this.compraId = compraId;
	}
	public CompraMedicamentoKeyModel() {}
	

	public int getMedicamentoId() {
		return medicamentoId;
	}
	public void setMedicamentoId(int medicamentoId) {
		this.medicamentoId = medicamentoId;
	}
	public int getCompraId() {
		return compraId;
	}
	public void setCompraId(int compraId) {
		this.compraId = compraId;
	}

}
