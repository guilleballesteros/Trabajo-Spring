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

import com.example.demo.model.CompraModel;
import com.example.demo.service.CompraService;


@Controller
@RequestMapping("/compras")
public class ComprasController {

	private static final String COMPRAS_VIEW="compra";
	private static final String FORM_VIEW="comprasForm";

	@Autowired
	@Qualifier("CompraService")
	private CompraService comprasServ;
	
	
	@GetMapping("/list")
	public ModelAndView listMedicamentos() {
		ModelAndView mav=new ModelAndView(COMPRAS_VIEW);
		mav.addObject("compras", comprasServ.listAllCompras());
		return mav;
	}
	@GetMapping("/add")
	public String addCompras(@Valid @ModelAttribute("compra") CompraModel compraModel, BindingResult bindingResult,
			RedirectAttributes flash,Model model) {
		if(bindingResult.hasErrors()) {
			return FORM_VIEW;
		}
		else {
			if(compraModel.getId()==0 ) {
				comprasServ.addCompra(compraModel);
				flash.addFlashAttribute("success","Compra insertado exitosamente");
			}
			else {
				comprasServ.updateCompra(compraModel);
				flash.addFlashAttribute("success","Compra Actualizado exitosamente");
			}
			return "redirect:/compras/list";
		}
		
	}
	
	@GetMapping(value={"/form","/form/{id}"})
	public String formCompra(@PathVariable(name="id", required=false) Integer id, Model model) {
		if(id==null) {
			model.addAttribute("compra", new CompraModel());
		}
		else {
			model.addAttribute("compra", comprasServ.findModel(id));
		}
		return FORM_VIEW;
	}
	
	@GetMapping("/delete/{id}")
	public String deleteCompra(@PathVariable("id") int id, RedirectAttributes flash) {
		if(comprasServ.removeCompra(id)==0) {
			flash.addAttribute("success","Compra eliminado satisfactoriamente");
		}
		else {
			flash.addAttribute("success", "No se ha podido eliminar la Compra");
		}
		return "redirect:/compras/list";
	}
}
