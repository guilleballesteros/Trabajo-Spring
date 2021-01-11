package com.example.demo.service.imp;

import java.util.List;
import java.util.stream.Collectors;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.example.demo.Repository.MedicoRepositorio;

import com.example.demo.entity.Medico;

import com.example.demo.model.MedicosModel;
import com.example.demo.service.MedicosService;



public class MedicosServiceImpl implements MedicosService {

	@Autowired
	@Qualifier("MedicoRepositorio")
	private MedicoRepositorio medicoRep;
	
	@Autowired
	private DozerBeanMapper dozer;

	@Override
	public List<MedicosModel> listAllMedicos() {
		return medicoRep.findAll().stream().map(c->transform(c)).collect(Collectors.toList());
	}

	@Override
	public MedicosModel findModel(int id) {
		return transform(medicoRep.findById(id).orElse(null));
	}

	@Override
	public Medico addMedico(MedicosModel medicoModel) {
		return medicoRep.save(transform(medicoModel));
	}

	@Override
	public int removeMedico(int id) {
		medicoRep.deleteById(id);
		return 0;
	}

	@Override
	public Medico transform(MedicosModel medicoModel) {
		return dozer.map(medicoModel, Medico.class);
	}

	@Override
	public MedicosModel transform(Medico medico) {
		return dozer.map(medico, MedicosModel.class); 
	}

	@Override
	public Medico updateMedico(MedicosModel medicoModel) {
		return medicoRep.save(transform(medicoModel));
	}
}
