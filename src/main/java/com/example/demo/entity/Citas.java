package com.example.demo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Citas {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@ManyToOne
	@JoinColumn(name="idPaciente")
	private User paciente;
	@ManyToOne
	@JoinColumn(name="idMedico")
	private User medico;
	@Column(length=100)
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha;
	
	
	private String observaciones;
	
	public Citas(int id, User paciente, User medico, Date fecha, String observaciones) {
		this.id = id;
		this.paciente = paciente;
		this.medico = medico;
		this.fecha = fecha;
		this.observaciones = observaciones;
	}
	public Citas() {}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getPaciente() {
		return paciente;
	}
	public void setPaciente(User paciente) {
		this.paciente = paciente;
	}
	public User getMedico() {
		return medico;
	}
	public void setMedico(User medico) {
		this.medico = medico;
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
