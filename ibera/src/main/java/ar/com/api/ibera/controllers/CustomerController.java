package ar.com.api.ibera.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.com.api.ibera.constants.ViewConstant;
import ar.com.api.ibera.models.Customer;
import ar.com.api.ibera.services.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	private static final Log LOGGER = LogFactory.getLog(CustomerController.class);
	
	
	public List<Customer> customersList = new ArrayList<Customer>();
	
	
	@Autowired
	@Qualifier("customerService")
	private CustomerService customerService;
	
	@GetMapping("/list")
	public String callCustomersPage(Model model){
		model.addAttribute("customer", customerService.getCustomers());
		return ViewConstant.CUSTOMER_VIEW;
	}
	
	/*Forma de llamar a la vista con get @GetMapping*/
	@GetMapping("/addcustomer")
	public String calladdPage(Model model){
		model.addAttribute("customer", new Customer());
		return ViewConstant.ADD_CUSTOMER_VIEW;
	}
	
	/*
	 * Metodo Post para agregar un nuevo registro
	 * url: /customer/add
	 * body {Customer}
	 */
	@PostMapping("/add")
	public ModelAndView addNewCustomer(@Valid @ModelAttribute("customer") Customer customer, BindingResult bindingResult){
		ModelAndView mv = new ModelAndView();
		if(bindingResult.hasErrors()){
			LOGGER.error("No se puede agregar hay errores!");
			mv.setViewName(ViewConstant.ADD_CUSTOMER_VIEW);
		}else{
			
			mv.addObject("customers", customerService.getCustomers());
			mv.setViewName(ViewConstant.CUSTOMER_VIEW);
			LOGGER.info("Se agrego un nuevo cliente a la lista : " + customer.toString());
		}
		
		return mv;
	}
	
}
