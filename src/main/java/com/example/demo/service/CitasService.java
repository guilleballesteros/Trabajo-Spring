package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Citas;
import com.example.demo.model.CitasModel;

public interface CitasService {
	List<CitasModel> listAllCitas();
	CitasModel findModel(int id);
	Citas addCita(CitasModel citaModel);
	int removeCita(int id);
	Citas transform(CitasModel citaModel);
	CitasModel transform(Citas cita);
	Citas updateCita(CitasModel citasModel);

}
