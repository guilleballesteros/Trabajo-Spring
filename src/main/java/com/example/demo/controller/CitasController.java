package com.example.demo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.CitasModel;
import com.example.demo.model.UserModel;
import com.example.demo.service.impl.CitasServiceImpl;
import com.example.demo.service.impl.UserServiceImpl;

@Controller
@RequestMapping("citas/")
public class CitasController {

	private static final Log Logger=LogFactory.getLog(CitasController.class);
	
	private static final String CITAS_VIEW="citas";
	private static final String FORM_VIEW="citasForm";
	
	@Autowired
	@Qualifier("CitasService")
	private CitasServiceImpl citasServ;
	
	@Autowired
	@Qualifier("userService")
	private UserServiceImpl userServ;
	
	@GetMapping("/form")
	public String formMedico( Model model) {
		model.addAttribute("cita",new CitasModel());
		model.addAttribute("Medicos",userServ.listAllmedicos());
		return FORM_VIEW;
	}
	
	@PostMapping("/add")
	public String addCita(@Valid @ModelAttribute("cita") CitasModel citaModel, BindingResult bindingResult,
			RedirectAttributes flash,Model mode, @RequestParam("idMedico") int idMedico) throws ParseException {
		if(bindingResult.hasErrors()) {
			flash.addFlashAttribute("error","los datos insertados no son correctos");
			return FORM_VIEW;
		}
		else {
			if(citaModel.getId()==0) {
				Logger.info("Fecha: "+citaModel.getFecha());
				UserModel medico=userServ.findModel(idMedico);
				if(citasServ.countByMedicoAndFecha(userServ.transform(medico), citaModel.getFecha())<10) {
					UserDetails userDetails= (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					UserModel paciente=userServ.findByUsername(userDetails.getUsername());
					citaModel.setMedico(medico);
					citaModel.setPaciente(paciente);
					citasServ.addCita(citaModel);
					flash.addFlashAttribute("success","Cita solicitada exitosamente");
				}
				else {
					flash.addFlashAttribute("error","el medico solicitado no esta disponible ese dia");
				}
			}
		}
		return FORM_VIEW;
	}
	
	
	
	
	
}
