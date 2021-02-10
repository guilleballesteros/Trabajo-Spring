package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.CarritoModel;
import com.example.demo.model.CompraMedicamentoKeyModel;
import com.example.demo.model.CompraMedicamentoModel;
import com.example.demo.model.CompraModel;
import com.example.demo.model.MedicamentoModel;
import com.example.demo.model.UserModel;
import com.example.demo.service.CompraService;
import com.example.demo.service.ComprasMedicamentosService;
import com.example.demo.service.MedicamentosService;
import com.example.demo.service.impl.UserServiceImpl;


@Controller
@RequestMapping("/compras")
public class ComprasController {

	private static final String COMPRAS_VIEW="compra";
	private static final String FORM_VIEW="comprasForm";
	private static final String CARRITO="carrito";
	
	private static final Log Logger=LogFactory.getLog(UserController.class);
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	@Qualifier("userService")
	private UserServiceImpl userServ;
	
	@Autowired
	@Qualifier("MedicamentosService")
	private MedicamentosService medicamentosServ;

	
	@Autowired
	@Qualifier("ComprasMedicamentosService")
	private ComprasMedicamentosService relacionServ;
	
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
			flash.addFlashAttribute("success","Compra eliminado satisfactoriamente");
		}
		else {
			flash.addFlashAttribute("success", "No se ha podido eliminar la Compra");
		}
		return "redirect:/compras/list";
	}
	
	@GetMapping("/carrito")
	public ModelAndView carrito() {
		ModelAndView mav=new ModelAndView(CARRITO);
		return mav;
	}
	
	@SuppressWarnings("null")
	@PostMapping("/addCarrito/{id}")
	public String addCarrito(@PathVariable("id") int id, @RequestParam("cantidad") int cantidad,RedirectAttributes flash) {
		boolean comp=false;
		float precioF=0;
		MedicamentoModel medicamento=medicamentosServ.findModel(id);
		@SuppressWarnings("unchecked")
		List<CarritoModel> carrito=(List<CarritoModel>) session.getAttribute("carrito");
		if(medicamento.getStock()>=cantidad) {
			if(carrito==null) {
				session.setAttribute("productos", cantidad);
				carrito=new ArrayList<CarritoModel>();
				carrito.add(new CarritoModel(medicamento,cantidad,medicamento.getPrecio()*cantidad));
			}
			else {
				session.setAttribute("productos",(Integer) session.getAttribute("productos")+cantidad);
				for(CarritoModel a:carrito) {
					if(a.getMedicamento().getId()==medicamento.getId()) {
						a.setNum(a.getNum()+cantidad);
						a.setPrecio(a.getPrecio()+(medicamento.getPrecio()*cantidad));
						comp=true;
					}
				}
				if(!comp) {
					carrito.add(new CarritoModel(medicamento,cantidad, medicamento.getPrecio()*cantidad));
				}
			}
			for(CarritoModel a: carrito) {
				precioF+=a.getPrecio();
			}
			session.setAttribute("precioF", precioF);
			session.setAttribute("carrito", carrito);
			flash.addFlashAttribute("success", "Product added to de shopping cart");
		}
		else {
			flash.addFlashAttribute("error", "we dont have enought stock");
		}
		return "redirect:/medicamentos/list";
		
	}
	
	@PostMapping("/removeCarrito/{id}")
	
	public String removeCarrito(@PathVariable("id") int id, @RequestParam("cantidad") int cantidad,RedirectAttributes flash) {
		float precioF=0;
		MedicamentoModel medicamento=medicamentosServ.findModel(id);
		@SuppressWarnings("unchecked")
		List<CarritoModel> carrito=(List<CarritoModel>) session.getAttribute("carrito");
		if(cantidad>0) {
			for(CarritoModel a:carrito) {
				if(a.getMedicamento().getId()==medicamento.getId()) {
					if(a.getNum()>cantidad) {
						a.setNum(a.getNum()-cantidad);
						a.setPrecio(a.getPrecio()-(medicamento.getPrecio()*cantidad));
					}else if (a.getNum()<cantidad) {
						flash.addFlashAttribute("error", "You can't delete more than you have");
						return "redirect:/compras/carrito";
					}
					else {
						Logger.info("antes");
						carrito.remove(carrito.indexOf(a));
						Logger.info("despues");
						break;
					}
				}
			}
			Logger.info("antes del if");
			if(carrito.isEmpty()) {
				Logger.info("Dentro del if");
				session.removeAttribute("carrito");
				session.removeAttribute("precioF");
				session.removeAttribute("productos");
			}
			else {
				Logger.info("hola");
				for(CarritoModel a: carrito) {
					precioF+=a.getPrecio();
				}
				session.setAttribute("carrito", carrito);
				session.setAttribute("precioF", precioF);
				session.setAttribute("productos",(Integer) session.getAttribute("productos")-cantidad);
			}
			
			Logger.info("adios");
			flash.addFlashAttribute("success", "Product deleted from the shopping cart");
		}
		return "redirect:/compras/carrito";
	}
	
	@GetMapping("/comprar")
	public String comprar(RedirectAttributes flash) {
		@SuppressWarnings("unchecked")
		List<CarritoModel> carrito=(List<CarritoModel>) session.getAttribute("carrito");
		if(carrito!= null) {
			UserDetails userDetails= (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserModel paciente=userServ.findByUsername(userDetails.getUsername());
			CompraModel compra=new CompraModel();
			compra.setFecha(new Date());
			compra.setPaciente(paciente);
			compra.setPrecio((Float)session.getAttribute("precioF"));
			compra=comprasServ.transform(comprasServ.addCompra(compra));
			
			for(CarritoModel a:carrito) {
				CompraMedicamentoModel relacion=new CompraMedicamentoModel(new CompraMedicamentoKeyModel(a.getMedicamento().getId(),compra.getId()),a.getMedicamento().getId(), compra.getId());
				relacionServ.addCompraMedicamento(relacion);
				
			}
			
			session.removeAttribute("carrito");
			session.removeAttribute("precioF");
			session.removeAttribute("productos");
			flash.addFlashAttribute("success","Purchase done successfully");
			
		}
		else {
			flash.addFlashAttribute("error","The shopping cart is empty");
		}
		
		return "redirect:/compras/carrito";
	}
}
