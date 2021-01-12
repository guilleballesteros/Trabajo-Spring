package com.example.demo.Repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Compra;

@Repository("CompraRepositorio")
public interface CompraRepositorio extends JpaRepository<Compra, Serializable>{

}
