package com.example.demo.service.imp;

import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.example.demo.Repository.MedicamentoRepositorio;
import com.example.demo.entity.Medicamento;
import com.example.demo.model.MedicamentoModel;
import com.example.demo.service.MedicamentosService;

public class MedicamentosServiceImpl implements MedicamentosService{

	@Autowired
	@Qualifier("MedicamentoRepositorio")
	private MedicamentoRepositorio medicamentoRep;
	
	@Autowired
	private DozerBeanMapper dozer;
	
	@Override
	public List<MedicamentoModel> listAllMedicamentos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MedicamentoModel findModel(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Medicamento addMedicamento(MedicamentoModel medicamentoModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int removeMedicamento(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Medicamento updateMedicamento(MedicamentoModel medicamentoModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Medicamento transform(MedicamentoModel medicamentoModel) {
		return dozer.map(medicamentoModel, Medicamento.class);
	}

	@Override
	public MedicamentoModel transform(Medicamento medicamento) {
		return dozer.map(medicamento, MedicamentoModel.class); 
	}

}
