package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.CompraMedicamentoRepositorio;
import com.example.demo.entity.CompraMedicamento;
import com.example.demo.entity.Medicamento;
import com.example.demo.model.CompraMedicamentoModel;
import com.example.demo.service.ComprasMedicamentosService;


@Service("ComprasMedicamentosService")
public class ComprasMedicamentosServiceImpl implements ComprasMedicamentosService{
	@Autowired
	@Qualifier("CompraMedicamento")
	private CompraMedicamentoRepositorio compramedicamentoRep;
	//prueba
	@Autowired
	private DozerBeanMapper dozer;
	
	@Override
	public List<CompraMedicamentoModel> listAllComprasMedicamentos() {
		return compramedicamentoRep.findAll().stream().map(c->transform(c)).collect(Collectors.toList());
	}

	@Override
	public CompraMedicamentoModel findModel(int id) {
		return transform(compramedicamentoRep.findById(id).orElse(null));
	}

	@Override
	public CompraMedicamento addCompraMedicamento(CompraMedicamentoModel compramedicamentoModel) {
		return compramedicamentoRep.save(transform(compramedicamentoModel));
	}

	@Override
	public int removeCompraMedicamento(int id) {
		compramedicamentoRep.deleteById(id);
		return 0;
	}


	@Override
	public CompraMedicamentoModel transform(CompraMedicamento compramedicamento) {
		return dozer.map(compramedicamento, CompraMedicamentoModel.class); 
	}

	@Override
	public CompraMedicamento updateCompraMedicamento(CompraMedicamentoModel compramedicamentoModel) {
		return compramedicamentoRep.save(transform(compramedicamentoModel));
	}

	@Override
	public CompraMedicamento transform(CompraMedicamentoModel compramedicamentoModel) {
		return dozer.map(compramedicamentoModel, CompraMedicamento.class);
	}

}
