package com.example.demo.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

public class CompraModel {

	private int id;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha;
	private float precio;
	private UserModel Paciente;
	private List<MedicamentoModel> medicamentos;
	public CompraModel() {
	}
	public CompraModel(int id, Date fecha, float precio, UserModel idPaciente) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.precio = precio;
		this.Paciente = idPaciente;
	}
	

	public List<MedicamentoModel> getMedicamentos() {
		return medicamentos;
	}
	public void setMedicamentos(List<MedicamentoModel> medicamentos) {
		this.medicamentos = medicamentos;
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
