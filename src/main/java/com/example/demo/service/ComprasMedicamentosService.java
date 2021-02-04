package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.CompraMedicamento;
import com.example.demo.model.CompraMedicamentoModel;



public interface ComprasMedicamentosService {
	List<CompraMedicamentoModel> listAllComprasMedicamentos();
	CompraMedicamentoModel findModel(int id);
	CompraMedicamento addCompraMedicamento(CompraMedicamentoModel compramedicamentoModel);
	int removeCompraMedicamento(int id);
	CompraMedicamento transform(CompraMedicamentoModel compramedicamentoModel);
	CompraMedicamentoModel transform(CompraMedicamento compramedicamento);
	CompraMedicamento updateCompraMedicamento(CompraMedicamentoModel compramedicamentoModel);
}
