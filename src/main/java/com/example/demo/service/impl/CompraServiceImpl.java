package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.CompraRepositorio;
import com.example.demo.entity.Compra;
import com.example.demo.model.CompraModel;
import com.example.demo.service.CompraService;

@Service("CompraService")
public class CompraServiceImpl implements CompraService{
	
	@Autowired
	@Qualifier("CompraRepositorio")
	private CompraRepositorio compraRep;
	
	@Autowired
	private DozerBeanMapper dozer;

	@Override
	public List<CompraModel> listAllCompras() {
		return compraRep.findAll().stream().map(c->transform(c)).collect(Collectors.toList());
	}

	@Override
	public CompraModel findModel(int id) {
		return transform(compraRep.findById(id).orElse(null));
	}

	@Override
	public Compra addCompra(CompraModel compraModel) {
		return compraRep.save(transform(compraModel));
	}

	@Override
	public int removeCompra(int id) {
		compraRep.deleteById(id);
		return 0;
	}

	@Override
	public Compra transform(CompraModel compraModel) {
		return dozer.map(compraModel, Compra.class);
	}

	@Override
	public CompraModel transform(Compra compra) {
		return dozer.map(compra, CompraModel.class); 
	}

	@Override
	public Compra updateCompra(CompraModel compraModel) {
		return compraRep.save(transform(compraModel));
	}

}
