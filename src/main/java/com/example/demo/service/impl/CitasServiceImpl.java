package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.CitasRepositorio;
import com.example.demo.entity.Citas;
import com.example.demo.model.CitasModel;
import com.example.demo.service.CitasService;

@Service("CitasService")
public class CitasServiceImpl implements CitasService{
	
	@Autowired
	@Qualifier("CitasRepositorio")
	private CitasRepositorio citasRep;
	
	@Autowired
	private DozerBeanMapper dozer;

	@Override
	public List<CitasModel> listAllCitas() {
		return citasRep.findAll().stream().map(c->transform(c)).collect(Collectors.toList());
	}

	@Override
	public CitasModel findModel(int id) {
		return transform(citasRep.findById(id).orElse(null));
	}

	@Override
	public Citas addCita(CitasModel citaModel) {
		return citasRep.save(transform(citaModel));
	}

	@Override
	public int removeCita(int id) {
		citasRep.deleteById(id);
		return 0;
	}

	@Override
	public Citas transform(CitasModel citaModel) {
		return dozer.map(citaModel, Citas.class);
	}

	@Override
	public CitasModel transform(Citas cita) {
		return dozer.map(cita, CitasModel.class); 
	}

	@Override
	public Citas updateCita(CitasModel citasModel) {
		return citasRep.save(transform(citasModel));
	}

}
