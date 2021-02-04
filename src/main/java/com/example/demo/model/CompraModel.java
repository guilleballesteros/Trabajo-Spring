package com.example.demo.model;

import java.util.Date;

public class CompraModel {

	private int id;
	private Date fecha;
	private float precio;
	private UserModel Paciente;
	public CompraModel() {
		super();
	}
	public CompraModel(int id, Date fecha, float precio, UserModel idPaciente) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.precio = precio;
		this.Paciente = idPaciente;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public float getPrecio() {
		return precio;
	}
	public void setPrecio(float precio) {
		this.precio = precio;
	}
	public UserModel getPaciente() {
		return Paciente;
	}
	public void setPaciente(UserModel idPaciente) {
		this.Paciente = idPaciente;
	}
	
}
