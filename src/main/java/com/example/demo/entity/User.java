package com.example.demo.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class User {
	
	@Id
	@GeneratedValue
	private int id;
	
	@Column(name="username",unique=true,nullable=false)
	private String username;
	
	@Column(name="password",nullable=false)
	@Size(max=100)
	private String password;
	@Column(length=100)
	private String nombre;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="paciente")
	private List<Compra> compras;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="paciente")
	private List<Citas> citasPaciente;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="medico")
	private List<Citas> citasMedico;
	
	
	private String direccion;
	
	private String foto;
	
	private String apellidos;
	
	private int edad;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaalta;
	
	private String especialidad;
	
	private boolean enabled;
	
	private String role;
	
	private String token;

	
	public User(int id, String username, @Size(max = 100) String password, String nombre, List<Compra> compras,
			List<Citas> citasPaciente, List<Citas> citasMedico, String direccion, String foto, String apellidos,
			int edad, Date fechaalta, String especialidad, boolean enabled, String role, String token) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.nombre = nombre;
		this.compras = compras;
		this.citasPaciente = citasPaciente;
		this.citasMedico = citasMedico;
		this.direccion = direccion;
		this.foto = foto;
		this.apellidos = apellidos;
		this.edad = edad;
		this.fechaalta = fechaalta;
		this.especialidad = especialidad;
		this.enabled = enabled;
		this.role = role;
		this.token = token;
	}

	

	public User() {}

	public List<Compra> getCompras() {
		return compras;
	}


	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}


	public List<Citas> getCitasPaciente() {
		return citasPaciente;
	}


	public void setCitasPaciente(List<Citas> citasPaciente) {
		this.citasPaciente = citasPaciente;
	}


	public List<Citas> getCitasMedico() {
		return citasMedico;
	}


	public void setCitasMedico(List<Citas> citasMedico) {
		this.citasMedico = citasMedico;
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
	
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", nombre=" + nombre
				+ ", direccion=" + direccion + ", foto=" + foto + ", apellidos=" + apellidos + ", edad=" + edad
				+ ", fechaalta=" + fechaalta + ", especialidad=" + especialidad + ", enabled=" + enabled + ", role="
				+ role + ", token=" + token + "]";
	}


	
	
	
	
	

}
