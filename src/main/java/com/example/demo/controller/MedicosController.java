package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
	
import com.example.demo.model.MedicosModel;
import com.example.demo.service.MedicosService;

@Controller
@RequestMapping("medicos/")
public class MedicosController {
	
	private static final String MEDICOS_VIEW="medicos";
	private static final String FORM_VIEW="medicosForm";
	
	@Autowired
	@Qualifier("MedicosService")
	private MedicosService medicosServ;
	
	@GetMapping("/list")
	public ModelAndView listMedicos() {
		ModelAndView mav=new ModelAndView(MEDICOS_VIEW);
		mav.addObject("medicos", medicosServ.listAllMedicos());
		return mav;
	}
	
	@GetMapping("/add")
	public String addMedicamentos(@Valid @ModelAttribute("medicos") MedicosModel medicosModel, BindingResult bindingResult,
			RedirectAttributes flash,Model model) {
		if(bindingResult.hasErrors()) {
			return FORM_VIEW;
		}
		else {
			if(medicosModel.getId()==0 ) {
				medicosServ.addMedico(medicosModel);
				flash.addFlashAttribute("success","Medico insertado exitosamente");
			}
			else {
				medicosServ.updateMedico(medicosModel);
				flash.addFlashAttribute("success","Medicos Actualizado exitosamente");
			}
			return "redirect:/medicos/list";
		}
		
	}
	
	@GetMapping(value={"/form","/form/{id}"})
	public String formMedico(@PathVariable(name="id", required=false) Integer id, Model model) {
		if(id==null) {
			model.addAttribute("medico", new MedicosModel());
		}
		else {
			model.addAttribute("medico", medicosServ.findModel(id));
		}
		return FORM_VIEW;
	}
	
	@GetMapping("/delete/{id}")
	public String deleteMedicamento(@PathVariable("id") int id, RedirectAttributes flash) {
		if(medicosServ.removeMedico(id)==0) {
			flash.addAttribute("success","Medico eliminado satisfactoriamente");
		}
		else {
			flash.addAttribute("success", "No se ha podido eliminar el medico");
		}
		return "redirect:/medicos/list";
	}

}
