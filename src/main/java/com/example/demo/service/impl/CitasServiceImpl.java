package com.example.demo.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.CitasRepositorio;
import com.example.demo.controller.CitasController;
import com.example.demo.entity.Citas;
import com.example.demo.entity.User;
import com.example.demo.model.CitasModel;
import com.example.demo.model.UserModel;
import com.example.demo.service.CitasService;

@Service("CitasService")
public class CitasServiceImpl implements CitasService{
	private static final Log Logger=LogFactory.getLog(CitasServiceImpl.class);
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
		Logger.info("antes de crear la cita");
		return citasRep.save(transform(citaModel));
	}

	@Override
	public int removeCita(int id) {
		citasRep.deleteById(id);
		return 0;
	}

	@Override
	public Citas transform(CitasModel citaModel) {
		
		Citas cita=dozer.map(citaModel, Citas.class);
		cita.setFecha(citaModel.getFecha());
		return cita;
	}

	@Override
	public CitasModel transform(Citas cita) {
		CitasModel citaModel=dozer.map(cita, CitasModel.class);
		citaModel.setFecha(cita.getFecha());
		return citaModel;
	}

	@Override
	public Citas updateCita(CitasModel citasModel) {
		return citasRep.save(transform(citasModel));
	}
	
	public int countByMedicoAndFecha(com.example.demo.entity.User medico, Date fecha) {
		Logger.info("antes de buscar");
		int citas= citasRep.countByMedicoAndFecha(medico, fecha);
		Logger.info("el numero de citas es:"+citas);
		
		return citas;
	}
	
	public List<CitasModel> findByPaciente(User paciente){
		return citasRep.findByPaciente(paciente).
				stream().map(c-> transform(c)).collect(Collectors.toList());
		
	}

}
