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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.UserModel;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.UserServiceImpl;
import com.example.demo.upload.FileSystemStorageService;

@Controller
@RequestMapping("users/")
public class UserController {
	
	private static final String PACIENTES_VIEW="pacientes";
	private static final String MEDICOS_VIEW="medicos";
	private static final String FORM_PACIENTE="pacientesForm";
	private static final String FORM_MEDICO="medicosForm";
	
	@Autowired
	@Qualifier("userService")
	private UserServiceImpl userServ;
	
	@Autowired
	private FileSystemStorageService storageService;
	
	@GetMapping("/listPacientes")
	public ModelAndView listPacientes() {
		ModelAndView mav=new ModelAndView(PACIENTES_VIEW);
		mav.addObject("pacientes", userServ.listAllpacientes());
		return mav;
	}
	
	@GetMapping("/listMedicos")
	public ModelAndView listMedicos() {
		ModelAndView mav=new ModelAndView(PACIENTES_VIEW);
		mav.addObject("pacientes", userServ.listAllmedicos());
		return mav;
	}
	
	@PostMapping("/addPaciente")
	public String addPaciente(@Valid @ModelAttribute("user") UserModel userModel, BindingResult bindingResult,
			RedirectAttributes flash,Model model, @RequestParam("file") MultipartFile file) {
		if(bindingResult.hasErrors()) {
			return FORM_PACIENTE;
		}
		else {
			if(userModel.getId()==0 ) {
				userModel=userServ.transform(userServ.register(userServ.transform(userModel)));
				if(!file.isEmpty()) {
					String imagen = storageService.store(file, userModel.getId());
					userModel.setFoto(MvcUriComponentsBuilder.fromMethodName(FileUploadController.class, "serverFile", imagen).build().toString());
					userServ.updateUser(userModel);
				}
				flash.addFlashAttribute("success","Paciente creado exitosamente");
			}
			else {
				if(!file.isEmpty()) {
					if(userModel.getFoto()!=null) {
						storageService.delete(userModel.getFoto());
					}
					String imagen = storageService.store(file, userModel.getId());
					userModel.setFoto(MvcUriComponentsBuilder.fromMethodName(FileUploadController.class, "serverFile", imagen).build().toString());
				}
				else {
					UserModel paciente = userServ.findModel(userModel.getId());
					userModel.setFoto(paciente.getFoto());
				}
				userServ.updateUser(userModel);
				flash.addFlashAttribute("success","Paciente Actualizado exitosamente");
			}
			return "redirect:/users/listPacientes";
		}
		
	}
	
	@PostMapping("/addMedico")
	public String addMedicamentos(@Valid @ModelAttribute("medicos") UserModel userModel, BindingResult bindingResult,
			RedirectAttributes flash,Model model) {
		if(bindingResult.hasErrors()) {
			return FORM_MEDICO;
		}
		else {
			if(userModel.getId()==0 ) {
				userServ.addUser(userModel);
				flash.addFlashAttribute("success","Medico insertado exitosamente");
			}
			else {
				userServ.updateUser(userModel);
				flash.addFlashAttribute("success","Medicos Actualizado exitosamente");
			}
			return "redirect:/users/listMedicos";
		}
		
	}
	
	@GetMapping(value={"/formMedico","/formMedico/{id}"})
	public String formMedico(@PathVariable(name="id", required=false) Integer id, Model model) {
		if(id==null) {
			model.addAttribute("medico", new UserModel());
		}
		else {
			model.addAttribute("medico", userServ.findModel(id));
		}
		return FORM_MEDICO;
	}
	
	@GetMapping(value={"/formPaciente","/formPaciente/{id}"})
	public String formPaciente(@PathVariable(name="id", required=false) Integer id, Model model) {
		if(id==null) {
			model.addAttribute("paciente", new UserModel());
		}
		else {
			model.addAttribute("paciente", userServ.findModel(id));
		}
		return FORM_PACIENTE;
	}
	
	
	@GetMapping("/deletePaciente/{id}")
	public String deletePaciente(@PathVariable("id") int id, RedirectAttributes flash) {
		if(userServ.removeUser(id)==0) {
			flash.addAttribute("success","Paciente eliminado satisfactoriamente");
		}
		else {
			flash.addAttribute("success", "No se ha podido eliminar el paciente");
		}
		return "redirect:/users/listPacientes";
	}
	
	@GetMapping("/deleteMedico/{id}")
	public String deleteMedico(@PathVariable("id") int id, RedirectAttributes flash) {
		if(userServ.removeUser(id)==0) {
			flash.addAttribute("success","Medico eliminado satisfactoriamente");
		}
		else {
			flash.addAttribute("success", "No se ha podido eliminar el medico");
		}
		return "redirect:/medicos/list";
	}

}
