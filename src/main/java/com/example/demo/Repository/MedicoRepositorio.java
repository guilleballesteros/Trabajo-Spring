package com.example.demo.Repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Medico;
import com.example.demo.entity.Citas;
import com.example.demo.model.MedicosModel;

@Repository("MedicoRepositorio")
public interface MedicoRepositorio extends JpaRepository<Medico, Serializable>{
	//public   abstract List<Citas> getCitas(MedicosModel medicosModel);
}
