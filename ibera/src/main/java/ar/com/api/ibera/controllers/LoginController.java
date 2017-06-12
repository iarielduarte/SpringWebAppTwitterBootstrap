package ar.com.api.ibera.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ar.com.api.ibera.constants.ViewConstant;
import ar.com.api.ibera.models.UserCredential;
import ar.com.api.ibera.services.CustomerService;

@Controller
public class LoginController {
	
	private static final Log LOGGER = LogFactory.getLog(LoginController.class);
	
	@Autowired
	@Qualifier("customerService")
	private CustomerService customerService;
	
	@GetMapping("/")
	public String redirectToLogin(){
		LOGGER.info("METHOD: redirectToLogin()");
		return "redirect:/login";
	}
	
	@GetMapping("/login")
	public String showLoginPage(Model model, 
			@RequestParam(name="error", required=false) String error, 
			@RequestParam(name="logout", required=false) String logout){
		LOGGER.info("METHOD: showLoginPage() -- PARAMS: ERROR:"+ error+"LOGOUT:"+logout );
		model.addAttribute("error", error);
		model.addAttribute("logout", logout);
		model.addAttribute("userCredentials", new UserCredential());
		return ViewConstant.LOGIN_VIEW;
	}
	
	@PostMapping("/logincheck")
	 public String loginCheck(Model model, @ModelAttribute(name="userCredentials") UserCredential uc){
		LOGGER.info("METHOD: loginCheck() -- PARAMS USER:" + uc.toString());
		if(uc.getUsername().equals("user") && uc.getPassword().equals("user")){
			model.addAttribute("customers", customerService.getCustomers());
			return ViewConstant.CUSTOMER_VIEW;
		}
		LOGGER.info("METHOD: redirect login with error");
		return "redirect:/login?error";
	}
}
