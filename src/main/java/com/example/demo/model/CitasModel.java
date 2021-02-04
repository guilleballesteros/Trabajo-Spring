package com.example.demo.model;

import java.util.Date;

public class CitasModel {

	private int id;
	private UserModel idPaciente;
	private UserModel idMedico;
	private Date fecha;
	private String observaciones;
	public CitasModel() {
		super();
	}
	public CitasModel(int id, UserModel idPaciente, UserModel idMedico, Date fecha, String observaciones) {
		super();
		this.id = id;
		this.idPaciente = idPaciente;
		this.idMedico = idMedico;
		this.fecha = fecha;
		this.observaciones = observaciones;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public UserModel getIdPaciente() {
		return idPaciente;
	}
	public void setIdPaciente(UserModel idPaciente) {
		this.idPaciente = idPaciente;
	}
	public UserModel getIdMedico() {
		return idMedico;
	}
	public void setIdMedico(UserModel idMedico) {
		this.idMedico = idMedico;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
}
