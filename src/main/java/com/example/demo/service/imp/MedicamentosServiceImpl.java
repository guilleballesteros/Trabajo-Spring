package com.example.demo.service.imp;

import java.util.List;
import java.util.stream.Collectors;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.MedicamentoRepositorio;
import com.example.demo.entity.Medicamento;
import com.example.demo.model.MedicamentoModel;
import com.example.demo.service.MedicamentosService;

@Service("MedicamentosService")
public class MedicamentosServiceImpl implements MedicamentosService{

	@Autowired
	@Qualifier("MedicamentoRepositorio")
	private MedicamentoRepositorio medicamentoRep;
	
	@Autowired
	private DozerBeanMapper dozer;
	
	@Override
	public List<MedicamentoModel> listAllMedicamentos() {
		return medicamentoRep.findAll().stream().map(c->transform(c)).collect(Collectors.toList());
	}

	@Override
	public MedicamentoModel findModel(int id) {
		return transform(medicamentoRep.findById(id).orElse(null));
	}

	@Override
	public Medicamento addMedicamento(MedicamentoModel medicamentoModel) {
		return medicamentoRep.save(transform(medicamentoModel));
	}

	@Override
	public int removeMedicamento(int id) {
		medicamentoRep.deleteById(id);
		return 0;
	}

	@Override
	public Medicamento updateMedicamento(MedicamentoModel medicamentoModel) {
		return medicamentoRep.save(transform(medicamentoModel));
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
