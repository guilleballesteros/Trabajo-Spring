package com.example.demo.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
@Entity
public class Medicamento {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column(length=100)
	private String nombre;
	private String descripcion;
	private String receta;
	private float precio;
	private int stock;
	
	@ManyToMany
	@JoinTable(
		name="compra_medicamentos",
		joinColumns=@JoinColumn(name="idMedicamento"),
		inverseJoinColumns = @JoinColumn(name="idCompra")
			)
	private List<Compra> compras;

	public Medicamento(int id, String nombre, String descripcion, String receta, float precio, int stock,
			List<Compra> compras) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.receta = receta;
		this.precio = precio;
		this.stock = stock;
		this.compras = compras;
	}
	public Medicamento() {}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getReceta() {
		return receta;
	}
	public void setReceta(String receta) {
		this.receta = receta;
	}
	public float getPrecio() {
		return precio;
	}
	public void setPrecio(float precio) {
		this.precio = precio;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public List<Compra> getCompras() {
		return compras;
	}
	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}
	
	
}
