package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.User;
import com.example.demo.model.UserModel;
import com.example.demo.service.impl.UserServiceImpl;
import com.example.demo.upload.FileSystemStorageService;

@Controller
public class LoginController {
	
	@Autowired
	@Qualifier("userService")
	private UserServiceImpl userservice;
	
	@Autowired
	private FileSystemStorageService storageService;
	
	@GetMapping("/auth/login")
	public String login(Model model, @RequestParam(name="error", required=false) String error,
			@RequestParam(name="logout", required=false) String logout) {
		model.addAttribute("user", new User());
		model .addAttribute("error",error);
		model.addAttribute("logout", logout);
		return "login";
	}
	@GetMapping("/login-post")
	public String loginPost() {
		return "redirect:/";
	}
	
	@GetMapping("/auth/registerForm")
	public String RegisterForm(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}
	
	@PostMapping("/auth/register")
	public String Register(@ModelAttribute User user, RedirectAttributes flash, BindingResult bindingResult, @RequestParam("file") MultipartFile file) {
		if(bindingResult.hasErrors()) {
			return "redirect:/auth/registerForm";
		}
		else {
			user=userservice.register(user);
			if(!file.isEmpty()) {
				String imagen = storageService.store(file, user.getId()); 
				user.setFoto(MvcUriComponentsBuilder.fromMethodName(FileUploadController.class, "serverFile", imagen).build().toString());
				userservice.updateUser(userservice.transform(user));
			}
			flash.addFlashAttribute("success","Te has registrado exitosamente");
			return "redirect:/auth/login";
		}
	}

}
