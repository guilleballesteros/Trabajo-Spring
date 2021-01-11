package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Medicamento;
import com.example.demo.entity.Medico;
import com.example.demo.model.MedicosModel;

public interface MedicosService {
	List<MedicosModel> listAllMedicos();
	MedicosModel findModel(int id);
	Medico addMedico(MedicosModel medicoModel);
	int removeMedico(int id);
	Medico transform(MedicosModel medicoModel);
	MedicosModel transform(Medico medico);
	Medico updateMedico(MedicosModel medicoModel);
}
