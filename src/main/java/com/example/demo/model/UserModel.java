package com.example.demo.model;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;


public class UserModel {

	private int id;
	
	private String username;
	
	private String password;
	
	private String nombre;
	
	private String direccion;
	
	private String foto;
	
	private String apellidos;
	
	private String verificationCode;
	
	private int edad;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaalta;
	
	private String especialidad;
	
	private boolean enabled;
	
	private String role;
	
	private String token;
	
	
	public UserModel(int id, String username, String password, String nombre, String direccion, String foto,
			String apellidos, int edad, Date fechaalta, String especialidad, boolean enabled, String role,
			String token, String verification) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.nombre = nombre;
		this.direccion = direccion;
		this.foto = foto;
		this.apellidos = apellidos;
		this.edad = edad;
		this.fechaalta = fechaalta;
		this.especialidad = especialidad;
		this.enabled = enabled;
		this.role = role;
		this.token = token;
		this.verificationCode=verification;
	}
	
	
	
	public UserModel() {}
	
	

	public String getVerificationCode() {
		return verificationCode;
	}



	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public Date getFechaalta() {
		return fechaalta;
	}

	public void setFechaalta(Date fechaalta) {
		this.fechaalta = fechaalta;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
	@Override
	public String toString() {
		return "UserModel [id=" + id + ", username=" + username + ", password=" + password + ", nombre=" + nombre
				+ ", direccion=" + direccion + ", foto=" + foto + ", apellidos=" + apellidos + ", edad=" + edad
				+ ", fechaalta=" + fechaalta + ", especialidad=" + especialidad + ", enabled=" + enabled + ", role="
				+ role + ", token=" + token + "]";
	}
	
}
