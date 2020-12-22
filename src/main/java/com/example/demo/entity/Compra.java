package com.example.demo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Compra {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column(length=100)
	private Date fecha;
	private float precio;
	
	@ManyToOne
	@JoinColumn(name="idPaciente")
	private Paciente paciente;

	public Compra(int id, Date fecha, float precio, Paciente paciente) {
		this.id = id;
		this.fecha = fecha;
		this.precio = precio;
		this.paciente = paciente;
	}
	
	public Compra() {}
	
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

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	
	

}
