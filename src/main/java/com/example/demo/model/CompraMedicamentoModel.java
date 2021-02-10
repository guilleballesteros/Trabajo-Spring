package com.example.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.example.demo.entity.Compra;
import com.example.demo.entity.Medicamento;

public class CompraMedicamentoModel {
	
	
	private int id;
	
	
    private MedicamentoModel medicamento;
	
	
    private CompraModel compra;

	public CompraMedicamentoModel(MedicamentoModel medicamento, CompraModel compra) {
		this.medicamento = medicamento;
		this.compra = compra;
	}

	public CompraMedicamentoModel() {}
	
	public MedicamentoModel getMedicamento() {
		return medicamento;
	}

	public void setMedicamento(MedicamentoModel medicamento) {
		this.medicamento = medicamento;
	}

	public CompraModel getCompra() {
		return compra;
	}

	public void setCompra(CompraModel compra) {
		this.compra = compra;
	}

}
