package com.example.demo.controller;

import java.util.Date;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	
	private static final Log Logger=LogFactory.getLog(UserController.class);
	
	@Autowired
	@Qualifier("userService")
	private UserServiceImpl userServ;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
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
		ModelAndView mav=new ModelAndView(MEDICOS_VIEW);
		mav.addObject("medicos", userServ.listAllmedicos());
		return mav;
	}
	
	@PostMapping("/addPaciente")
	public String addPaciente(@Valid @ModelAttribute("paciente") UserModel userModel, BindingResult bindingResult,
			RedirectAttributes flash,Model model, @RequestParam("file") MultipartFile file) {
		//Comprueba que no haya valores nulos
		if(userModel.getNombre().isEmpty()||userModel.getApellidos().isEmpty()||userModel.getDireccion().isEmpty()||
				userModel.getUsername().isEmpty()||userModel.getEdad()==0|| userModel.getPassword().isEmpty()) {
			flash.addFlashAttribute("error","there can't be null values");
			return "redirect:/users/listPacientes";
		}
		//coprueba si el usuario ya existe
		else if(userServ.findByUsername(userModel.getUsername())!=null) {
			flash.addFlashAttribute("error","This user already exist");
			return "redirect:/users/formPaciente";
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
				if(userModel.getPassword().isEmpty()) {
					UserModel paciente = userServ.findModel(userModel.getId());
					userModel.setPassword(paciente.getPassword());
				}
				else {
					userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
				}
				userServ.updateUser(userModel);
				flash.addFlashAttribute("success","Paciente Actualizado exitosamente");
			}
			return "redirect:/users/listPacientes";
		}
		
	}
	
	@PostMapping("/addMedico")
	public String addMedico(@Valid @ModelAttribute("medico") UserModel userModel, BindingResult bindingResult,
			RedirectAttributes flash,Model model) {
		//comprueba que no haya valores nulos
		if(userModel.getUsername().isEmpty()||userModel.getPassword().isEmpty()||userModel.getNombre().isEmpty()||
				userModel.getApellidos().isEmpty()||userModel.getFechaalta()==null||
				userModel.getEspecialidad().isEmpty()||userModel.getEdad()==0) {
			flash.addFlashAttribute("error","There can't be null values");
			return "redirect:/users/formMedico";
		}
		//comprueba que no exista ya un usuario con ese nombre de usuario
		else if(userServ.findByUsername(userModel.getUsername())!=null) {
			flash.addFlashAttribute("error","This user already exist");
			return "redirect:/users/formMedico";
		}
		else {
			Date fechaActual=new Date();
			Date fechaalta=userModel.getFechaalta();
			//comprueba que la fecha introducida no sea mayor al dia actual
			if(fechaActual.before(fechaalta)) {
				flash.addFlashAttribute("error","Incorrect Date");
				return "redirect:/users/formMedico";
			}
			else {
				
				if(userModel.getId()==0 ) {
					userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
					userModel.setRole("ROLE_MEDICO");
					userModel.setEnabled(true);
					userServ.addUser(userModel);
					flash.addFlashAttribute("success","Medico insertado exitosamente");
				}
				else {
					if(userModel.getPassword().isEmpty()) {
						UserModel medico = userServ.findModel(userModel.getId());
						userModel.setPassword(medico.getPassword());
					}
					else {
						userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
					}
					userServ.updateUser(userModel);
					flash.addFlashAttribute("success","Medico Actualizado exitosamente");
				}
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
			UserModel medico= userServ.findModel(id);
			model.addAttribute("medico",medico);
			
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
		return "redirect:/users/listMedicos";
	}

}
