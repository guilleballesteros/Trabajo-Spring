package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

import com.example.demo.model.CitasModel;
import com.example.demo.model.UserModel;
import com.example.demo.service.impl.CitasServiceImpl;
import com.example.demo.service.impl.UserServiceImpl;
import com.example.demo.upload.FileSystemStorageService;

@Controller
@RequestMapping("pacientes/")
public class PacientesController {
	private static final Log Logger=LogFactory.getLog(UserController.class);
	
	@Autowired
	@Qualifier("userService")
	private UserServiceImpl userServ;
	
	@Autowired
	@Qualifier("CitasService")
	private CitasServiceImpl citasServ;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private FileSystemStorageService storageService;
	
	private static final String FORM_PACIENTE="pacientesUpdate";
	private static final String CITAS_LIST="listCitasPaciente";
	private static final String HISTORIAL="citas";
	@PreAuthorize("hasRole('ROLE_PACIENTE')")
	@GetMapping(value={"/form"})
	public String formPaciente(Model model) {
		UserDetails userDetails= (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("paciente", userServ.findByUsername(userDetails.getUsername()));
		return FORM_PACIENTE;
	}
	@PreAuthorize("hasRole('ROLE_PACIENTE')")
	@PostMapping("/update")
	public String addPaciente(@Valid @ModelAttribute("paciente") UserModel userModel, BindingResult bindingResult,
			RedirectAttributes flash,Model model, @RequestParam("file") MultipartFile file, @RequestParam("passwordN") String passwordN) {
		if(bindingResult.hasErrors()) {
			return FORM_PACIENTE;
		}
		else {
			UserModel paciente = userServ.findModel(userModel.getId());
			if(!file.isEmpty()) {
				if(userModel.getFoto()!=null) {
					storageService.delete(userModel.getFoto());
				}
				String imagen = storageService.store(file, userModel.getId());
				userModel.setFoto(MvcUriComponentsBuilder.fromMethodName(FileUploadController.class, "serverFile", imagen).build().toString());
			}
			else {
				userModel.setFoto(paciente.getFoto());
			}
			if(userModel.getPassword().isEmpty()) {
				
				userModel.setPassword(paciente.getPassword());
			}
			else {
				if(passwordEncoder.matches(userModel.getPassword(), paciente.getPassword())) {
					if(!passwordN.isEmpty()) {
						
						userModel.setPassword(passwordEncoder.encode(passwordN));
					}
				}
				else {
					
					userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
				}
			}
			userServ.updateUser(userModel);
			flash.addFlashAttribute("success","Su cuenta se ha actualizado exitosamente");
		}
		return "redirect:/users/listPacientes";
		
		
	}
	@PreAuthorize("hasRole('ROLE_PACIENTE')")
	@GetMapping("/listCitas")
	public ModelAndView listCitas() {
		UserDetails userDetails= (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserModel paciente=userServ.findByUsername(userDetails.getUsername());
		ModelAndView mav=new ModelAndView(CITAS_LIST);
		mav.addObject("citas", citasServ.findByPaciente(userServ.transform(paciente)));
		return mav;
	}
	
	@GetMapping("/historialPaciente/{id}")
	public ModelAndView historial(@PathVariable(name="id") Integer id) {
		UserModel paciente=userServ.findModel(id);
		ModelAndView mav=new ModelAndView(CITAS_LIST);
		mav.addObject("citas", citasServ.findByPaciente(userServ.transform(paciente)));
		return mav;
		
	}

}
