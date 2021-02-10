package com.example.demo.model;

public class CarritoModel {


	private MedicamentoModel medicamento;
	private int num;
	private float precio;
	public CarritoModel(MedicamentoModel medicamento, int num, float precio) {
		this.medicamento = medicamento;
		this.num = num;
		this.precio=precio;
	}
	
	public CarritoModel() {}
	


	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}
	
	public MedicamentoModel getMedicamento() {
		return medicamento;
	}

	public void setMedicamento(MedicamentoModel medicamento) {
		this.medicamento = medicamento;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
	

}
