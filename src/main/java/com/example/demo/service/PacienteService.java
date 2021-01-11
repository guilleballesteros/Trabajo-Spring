package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Paciente;
import com.example.demo.model.PacientesModel;



public interface PacienteService {
	List<PacientesModel> listAllPaciente();
	PacientesModel findModel(int id);
	Paciente addPaciente(PacientesModel pacienteModel);
	int removeMedicamento(int id);
	Paciente transform(PacientesModel medicamentoModel);
	PacientesModel transform(Paciente medicamento);
	Paciente updatePaciente(PacientesModel medicamentoModel);
}
