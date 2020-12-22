package com.example.demo.Repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Medicamento;

@Repository("MedicamentoRepositorio")
public interface MedicamentoRepositorio extends JpaRepository<Medicamento, Serializable>{

}
