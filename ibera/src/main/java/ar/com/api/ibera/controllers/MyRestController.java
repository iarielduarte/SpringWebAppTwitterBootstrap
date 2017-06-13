package ar.com.api.ibera.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.api.ibera.models.Customer;
import ar.com.api.ibera.services.CustomerService;

@RestController
@RequestMapping("/rest")
public class MyRestController {
	
	@Autowired
	@Qualifier("customerService")
	private CustomerService customerService;
	
	@GetMapping("/customers")
	public ResponseEntity<List<Customer>> getCustomers(){
		
		List<Customer> customers = new ArrayList<Customer>(); 
		customers = customerService.getCustomers();
		
		return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
		
	}

	
}
