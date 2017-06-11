package ar.com.api.ibera.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ar.com.api.ibera.models.Customer;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService{

	@Override
	public List<Customer> getCustomersList() {
		List<Customer> customers = new ArrayList<>();
		customers.add(new Customer("Pepe", ""));
		customers.add(new Customer("Gato", ""));
		customers.add(new Customer("Rush", ""));
		return customers;
	}

}
