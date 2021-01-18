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

import com.example.demo.model.PacientesModel;
import com.example.demo.service.PacienteService;



@Controller
@RequestMapping("/pacientes")
public class PacientesController {

	private static final String PACIENTES_VIEW="pacientes";
	private static final String FORM_VIEW="pacientesForm";
	
	@Autowired
	@Qualifier("PacienteService")
	private PacienteService pacientesServ;
	
	@GetMapping("/list")
	public ModelAndView listPacientes() {
		ModelAndView mav=new ModelAndView(PACIENTES_VIEW);
		mav.addObject("pacientes", pacientesServ.listAllPaciente());
		return mav;
	}
	@GetMapping("/add")
	public String addPacientes(@Valid @ModelAttribute("paciente") PacientesModel pacienteModel, BindingResult bindingResult,
			RedirectAttributes flash,Model model) {
		if(bindingResult.hasErrors()) {
			return FORM_VIEW;
		}
		else {
			if(pacienteModel.getId()==0 ) {
				pacientesServ.addPaciente(pacienteModel);
				flash.addFlashAttribute("success","Paciente insertado exitosamente");
			}
			else {
				pacientesServ.updatePaciente(pacienteModel);
				flash.addFlashAttribute("success","Paciente Actualizado exitosamente");
			}
			return "redirect:/pacientes/list";
		}
		
	}
	
	@GetMapping(value={"/form","/form/{id}"})
	public String formPaciente(@PathVariable(name="id", required=false) Integer id, Model model) {
		if(id==null) {
			model.addAttribute("paciente", new PacientesModel());
		}
		else {
			model.addAttribute("paciente", pacientesServ.findModel(id));
		}
		return FORM_VIEW;
	}
	@GetMapping("/delete/{id}")
	public String deletePaciente(@PathVariable("id") int id, RedirectAttributes flash) {
		if(pacientesServ.removePaciente(id)==0) {
			flash.addAttribute("success","Paciente eliminado satisfactoriamente");
		}
		else {
			flash.addAttribute("success", "No se ha podido eliminar el paciente");
		}
		return "redirect:/pacientes/list";
	}
}
