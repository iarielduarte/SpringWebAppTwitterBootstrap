package ar.com.api.ibera.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("username", user.getUsername());
		model.addAttribute("customers", customerService.getCustomers());
		return ViewConstant.CUSTOMER_VIEW;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	@GetMapping("/addcustomer")
	public String callAddPage(Model model){
		model.addAttribute("edit", false);
		model.addAttribute("customer", new Customer());
		return ViewConstant.ADD_CUSTOMER_VIEW;
	}
	
	@GetMapping("/editcustomer")
	public String callEditPage(@RequestParam(name="id", required=true) Integer id, Model model){
		Customer c = customerService.findCustomerById(id);
		model.addAttribute("customer", c);
		model.addAttribute("edit", true);
		return ViewConstant.ADD_CUSTOMER_VIEW;
	}
	
	
	@PostMapping("/add")
	public ModelAndView addNewCustomer(@Valid @ModelAttribute("customer") Customer customer, BindingResult bindingResult){
		ModelAndView mv = new ModelAndView();
		if(bindingResult.hasErrors()){
			LOGGER.error("No se puede agregar hay errores!");
			mv.setViewName(ViewConstant.ADD_CUSTOMER_VIEW);
		}else{
			if(customerService.saveCustomer(customer) != null){
				mv.addObject("customers", customerService.getCustomers());
				mv.addObject("result", 1);
				mv.setViewName(ViewConstant.CUSTOMER_VIEW);
				LOGGER.info("Customer has been saved!");
			}else{
				mv.addObject("result", 0);
				mv.setViewName(ViewConstant.CUSTOMER_VIEW);
				LOGGER.error("Customer hasn't been saved!");
			}
			
		}
		
		return mv;
	}
	
	@GetMapping("/remove")
	public ModelAndView removeCustomer(@RequestParam(name="id", required=true) Integer id){
		ModelAndView mv = new ModelAndView(ViewConstant.CUSTOMER_VIEW);
		if(id != null){
			Customer c = customerService.findCustomerById(id);
			LOGGER.info("Customer has been removed : " + c.toString());
			customerService.removeCustomer(c);
			mv.addObject("customers", customerService.getCustomers());
			mv.addObject("result", 3);
		}
		return mv;
	}
	
}
