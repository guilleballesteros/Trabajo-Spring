package com.example.demo.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class CompraMedicamento {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "Id_medicamento")
    private Medicamento medicamento;
	
	@ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="Id_Compra")
    private Compra compra;

	public CompraMedicamento(Medicamento medicamento, Compra compra) {
		this.medicamento = medicamento;
		this.compra = compra;
	}

	public CompraMedicamento() {}
	
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
