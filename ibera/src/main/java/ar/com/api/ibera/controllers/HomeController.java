package ar.com.api.ibera.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.com.api.ibera.models.Customer;

@Controller
@RequestMapping("/welcome")
public class HomeController {

	public static final String HOME_VIEW = "home";
	
	/*Forma de llamar a la vista con get @GetMapping*/
	@GetMapping("/home")
	public String callHomePageFirst(Model model){
		model.addAttribute("copyr", "Copyright Ariel Duarte | 2007");
		return HOME_VIEW;
	}
	
	/*Forma de llamar a la vista con get @RequestMapping*/
	@RequestMapping(value="/homemv", method=RequestMethod.GET)
	public ModelAndView callHomePageSecond(){
		ModelAndView mv = new ModelAndView(HOME_VIEW);
		mv.addObject("customer", new Customer("Coca-Cola",""));
		mv.addObject("copyr", "Copyright Ariel Duarte | 2007");
		return mv;
	}
	
}
