package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Compra;
import com.example.demo.model.CompraModel;

public interface CompraService {
	List<CompraModel> listAllCompras();
	CompraModel findModel(int id);
	Compra addCompra(CompraModel compraModel);
	int removeCompra(int id);
	Compra transform(CompraModel compraModel);
	CompraModel transform(Compra compra);
	Compra updateCompra(CompraModel compraModel);

}
