package com.example.demo.model;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

public class CitasModel {

	private int id;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha;
	private UserModel paciente;
	private UserModel medico;
	private String observaciones;
	public CitasModel() {}
	public CitasModel(int id, UserModel idPaciente, UserModel idMedico, Date fecha, String observaciones) {
		this.id = id;
		this.paciente = idPaciente;
		this.medico = idMedico;
		this.fecha = fecha;
		this.observaciones = observaciones;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public UserModel getPaciente() {
		return paciente;
	}
	public void setPaciente(UserModel idPaciente) {
		this.paciente = idPaciente;
	}
	public UserModel getMedico() {
		return medico;
	}
	public void setMedico(UserModel idMedico) {
		this.medico = idMedico;
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
