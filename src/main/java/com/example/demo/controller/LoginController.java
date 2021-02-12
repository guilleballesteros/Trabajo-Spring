package com.example.demo.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

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

import ch.qos.logback.classic.Logger;
import net.bytebuddy.utility.RandomString;

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
	public String Register(@ModelAttribute User user, RedirectAttributes flash, BindingResult bindingResult, @RequestParam("file") MultipartFile file) throws UnsupportedEncodingException, MessagingException {
		if(bindingResult.hasErrors()) {
			return "redirect:/auth/registerForm";
		}
		else {
			user=userservice.register(user);
			if(!file.isEmpty()) {
				String imagen = storageService.store(file, user.getId()); 
				user.setFoto(MvcUriComponentsBuilder.fromMethodName(FileUploadController.class, "serverFile", imagen).build().toString());
				
			}
			String randomCode = RandomString.make(64);
		    user.setVerificationCode(randomCode);
			userservice.updateUser(userservice.transform(user));
			flash.addFlashAttribute("success","Te has registrado exitosamente");
			return "redirect:/auth/login";
		}
	}
	@GetMapping("verify")
	public String verificar(@RequestParam("code") String code) {
		UserModel user =userservice.findByCode(code);
		if(user!=null) {
			user.setEnabled(true);
			userservice.updateUser(user);
		}
		
		return "redirect:/auth/login";
	}
	
	

}
