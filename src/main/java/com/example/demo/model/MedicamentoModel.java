package com.example.demo.model;

import java.util.List;

public class MedicamentoModel {

	private int id;
	private String nombre;
	private String descripcion;
	private String receta;
	private float precio;
	private int stock;
	private List<CompraModel> compras;
	public MedicamentoModel() {
	}
	public MedicamentoModel(int id, String nombre, String descripcion, String receta, float precio, int stock) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.receta = receta;
		this.precio = precio;
		this.stock = stock;
	}
	

	public List<CompraModel> getCompras() {
		return compras;
	}
	public void setCompras(List<CompraModel> compras) {
		this.compras = compras;
	}
	
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
	
}
