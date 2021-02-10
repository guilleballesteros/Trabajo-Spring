package com.example.demo.Repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Citas;
import com.example.demo.entity.User;
import com.example.demo.model.UserModel;

@Repository("CitasRepositorio")
public interface CitasRepositorio extends JpaRepository<Citas, Serializable> {
	public abstract int countByMedicoAndFecha(User medico, Date fecha);
	public abstract List<Citas> findByPaciente(User paciente);
}
