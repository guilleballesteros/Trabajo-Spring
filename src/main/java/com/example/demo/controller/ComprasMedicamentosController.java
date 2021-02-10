package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.CompraMedicamentoModel;
import com.example.demo.service.ComprasMedicamentosService;



public class ComprasMedicamentosController {
	
	private static final String COMPRAMEDICAMENTOS_VIEW="compramedicamentos";
	private static final String FORM_VIEW="compramedicamentosForm";

	@Autowired
	@Qualifier("ComprasMedicamentosService")
	private ComprasMedicamentosService compramedicamentosServ;
	
	
	@GetMapping("/list")
	public ModelAndView listMedicamentos() {
		ModelAndView mav=new ModelAndView(COMPRAMEDICAMENTOS_VIEW);
		mav.addObject("compramedicamentos", compramedicamentosServ.listAllComprasMedicamentos());
		return mav;
	}
	
	@PostMapping("/add")
	public String addMedicamentos(@Valid @ModelAttribute("compramedicamento") CompraMedicamentoModel compramedicamentoModel, BindingResult bindingResult,
			RedirectAttributes flash,Model model) {
		if(bindingResult.hasErrors()) {
			return FORM_VIEW;
		}
		else {
			
				compramedicamentosServ.addCompraMedicamento(compramedicamentoModel);
				flash.addFlashAttribute("success","Medicamento insertado exitosamente");
			
			return "redirect:/medicamentos/list";
		}
		
	}
	
	@GetMapping(value={"/form","/form/{id}"})
	public String formCompraMedicamento(@PathVariable(name="id", required=false) Integer id, Model model) {
		if(id==null) {
			model.addAttribute("compramedicamento", new CompraMedicamentoModel());
		}
		else {
			model.addAttribute("compramedicamento", compramedicamentosServ.findModel(id));
		}
		return FORM_VIEW;
	}
	
	@GetMapping("/delete/{id}")
	public String deleteCompraMedicamento(@PathVariable("id") int id, RedirectAttributes flash) {
		if(compramedicamentosServ.removeCompraMedicamento(id)==0) {
			flash.addAttribute("success","Compra del medicamento eliminado satisfactoriamente");
		}
		else {
			flash.addAttribute("success", "No se ha podido eliminar la compra del medicamento");
		}
		return "redirect:/compramedicamentos/list";
	}
}
