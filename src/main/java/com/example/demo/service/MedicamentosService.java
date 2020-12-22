package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Medicamento;
import com.example.demo.model.MedicamentoModel;

public interface MedicamentosService {
	List<MedicamentoModel> listAllMedicamentos();
	MedicamentoModel findModel(int id);
	Medicamento addMedicamento(MedicamentoModel medicamentoModel);
	int removeMedicamento(int id);
	Medicamento transform(MedicamentoModel medicamentoModel);
	MedicamentoModel transform(Medicamento medicamento);
	Medicamento updateMedicamento(MedicamentoModel medicamentoModel);
}
