package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.PacienteRepositorio;

import com.example.demo.entity.Paciente;

import com.example.demo.model.PacientesModel;
import com.example.demo.service.PacienteService;

@Service("PacienteService")
public class PacientesServiceImpl implements PacienteService {

	@Autowired
	@Qualifier("PacienteRepositorio")
	private PacienteRepositorio pacienteRep;
	
	@Autowired
	private DozerBeanMapper dozer;
	
	@Override
	public List<PacientesModel> listAllPaciente() {
		return pacienteRep.findAll().stream().map(c->transform(c)).collect(Collectors.toList());
	}

	@Override
	public PacientesModel findModel(int id) {
		return transform(pacienteRep.findById(id).orElse(null));
	}

	@Override
	public Paciente addPaciente(PacientesModel pacienteModel) {
		return pacienteRep.save(transform(pacienteModel));
	}

	@Override
	public int removePaciente(int id) {
		pacienteRep.deleteById(id);
		return 0;
	}

	@Override
	public Paciente transform(PacientesModel pacienteModel) {
		return dozer.map(pacienteModel, Paciente.class);
	}

	@Override
	public PacientesModel transform(Paciente paciente) {
		return dozer.map(paciente, PacientesModel.class);
	}

	@Override
	public Paciente updatePaciente(PacientesModel pacienteModel) {
		return pacienteRep.save(transform(pacienteModel));
	}
	

}
