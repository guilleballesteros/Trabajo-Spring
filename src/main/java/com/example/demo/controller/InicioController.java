package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class InicioController {
	private static final String inicio="inicio";
	@GetMapping("")
	public String inicio() {
		return inicio;
	}
}
