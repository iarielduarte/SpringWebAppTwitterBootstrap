package ar.com.api.ibera.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import ar.com.api.ibera.models.Customer;

@Controller
@RequestMapping("/home")
public class HomeController {

	private static final Log LOGGER = LogFactory.getLog(HomeController.class);
	
	public static final String HOME_VIEW = "home";
	public static final String ERROR_404 = "404";
	public static final String ERROR_500 = "500";
	public List<Customer> customersList = new ArrayList<Customer>();
	
	/*Forma de llamar a la vista con get @GetMapping*/
	@GetMapping("/customer")
	public String callHomePageFirst(Model model){
		model.addAttribute("customer", new Customer());
		model.addAttribute("customers", getCustomers());
		model.addAttribute("copyr", "Copyright Ariel Duarte | 2007");
		return HOME_VIEW;
	}
	
	/*Forma de llamar a la vista con get @RequestMapping
	 * Ejemplo enviar parametros con @RequestParam
	 * url : /welcome/request1?auth=Ariel Duarte
	 * */
	@RequestMapping(value="/request1", method=RequestMethod.GET)
	public ModelAndView request1(@RequestParam(name="auth", required=false, defaultValue="Ariel Duarte") String author){
		ModelAndView mv = new ModelAndView(HOME_VIEW);
		customersList = getCustomers();
		mv.addObject("customer", new Customer());
		mv.addObject("customers", getCustomers());
		mv.addObject("copyr", "Copyright " + author +" | 2007");
		return mv;
	}
	
	/*Forma de llamar a la vista con get @RequestMapping
	 * Ejemplo enviar parametros con @RequestParam
	 * url : /welcome/request2/Ariel Duarte
	 * */
	@GetMapping("/request2/{auth}")
	public ModelAndView request2(@PathVariable("auth") String author){
		ModelAndView mv = new ModelAndView(HOME_VIEW);
		mv.addObject("customers", getCustomers());
		mv.addObject("copyr", "Copyright " + author +" | 2007");
		return mv;
	}
	
	public List<Customer> getCustomers(){
		return customersList;
	}
	
	/*
	 * Metodo Post para agregar un nuevo registro
	 * url: /customer/add
	 * body {Customer}
	 */
	@PostMapping("/customer/add")
	public RedirectView addNewCustomer(@ModelAttribute("customer") Customer customer){
		customersList.add(customer);
		//int i = 6 / 0;
		//return "redirect:/home/customer";
		LOGGER.info("Se agrego un nuevo cliente a la lista : " + customer.toString());
		return new RedirectView("/home/customer");
	}
	
}
