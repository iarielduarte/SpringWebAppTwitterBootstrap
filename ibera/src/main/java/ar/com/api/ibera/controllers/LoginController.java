package ar.com.api.ibera.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ar.com.api.ibera.constants.ViewConstant;

@Controller
public class LoginController {
	
	private static final Log LOGGER = LogFactory.getLog(LoginController.class);
	
	
	@GetMapping("/login")
	public String showLoginPage(Model model, 
			@RequestParam(name="error", required=false) String error, 
			@RequestParam(name="logout", required=false) String logout){
		LOGGER.info("METHOD: showLoginPage() -- PARAMS: ERROR: "+ error+ " LOGOUT:"+ logout );
		model.addAttribute("error", error);
		model.addAttribute("logout", logout);
		return ViewConstant.LOGIN_VIEW;
	}
	
	@GetMapping({ "/loginsuccess", "/" })
	 public String loginCheck(){
		LOGGER.info("Login Sucess!");
		return "redirect:/customer/list";
		
	}
}
