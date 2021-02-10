package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.CarritoModel;
import com.example.demo.model.MedicamentoModel;
import com.example.demo.service.MedicamentosService;


@Controller
@RequestMapping("/medicamentos")
public class MedicamentosController {
	
	
	private static final String MEDICAMENTOS_VIEW="medicamentos";
	private static final String FORM_VIEW="medicamentosForm";
	
	private static final Log Logger=LogFactory.getLog(UserController.class);

	@Autowired
	@Qualifier("MedicamentosService")
	private MedicamentosService medicamentosServ;
	
	
	@GetMapping("/list")
	public ModelAndView listMedicamentos() {
		ModelAndView mav=new ModelAndView(MEDICAMENTOS_VIEW);
		mav.addObject("medicamentos", medicamentosServ.listAllMedicamentos());
		return mav;
	}
	
	@PostMapping("/add")
	public String addMedicamentos(@Valid @ModelAttribute("medicamento") MedicamentoModel medicamentoModel, BindingResult bindingResult,
			RedirectAttributes flash,Model model) {
		if(bindingResult.hasErrors()) {
			return FORM_VIEW;
		}
		else {
			if(medicamentoModel.getId()==0 ) {
				medicamentosServ.addMedicamento(medicamentoModel);
				flash.addFlashAttribute("success","Medicamento insertado exitosamente");
			}
			else {
				medicamentosServ.updateMedicamento(medicamentoModel);
				flash.addFlashAttribute("success","Medicamento Actualizado exitosamente");
			}
			return "redirect:/medicamentos/list";
		}
		
	}
	
	@GetMapping(value={"/form","/form/{id}"})
	public String formMedicamento(@PathVariable(name="id", required=false) Integer id, Model model) {
		if(id==null) {
			model.addAttribute("medicamento", new MedicamentoModel());
		}
		else {
			model.addAttribute("medicamento", medicamentosServ.findModel(id));
		}
		return FORM_VIEW;
	}
	
	@GetMapping("/delete/{id}")
	public String deleteMedicamento(@PathVariable("id") int id, RedirectAttributes flash) {
		if(medicamentosServ.removeMedicamento(id)==0) {
			flash.addAttribute("success","Medicamento eliminado satisfactoriamente");
		}
		else {
			flash.addAttribute("success", "No se ha podido eliminar el medicamento");
		}
		return "redirect:/medicamentos/list";
	}
	
	
}
