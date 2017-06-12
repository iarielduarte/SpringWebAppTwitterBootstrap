package ar.com.api.ibera.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ar.com.api.ibera.models.Customer;
import ar.com.api.ibera.repositories.CustomerRepository;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	@Qualifier("customerRepository")
	private CustomerRepository customerRepository;
	
	@Override
	public Customer saveCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public Customer removeCustomer(Customer customer) {
		customerRepository.delete(customer);
		return customer;
	}

	@Override
	public Customer updateCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public List<Customer> getCustomers() {
		return customerRepository.findAll();
	}

	@Override
	public Customer findCustomerById(Integer id) {
		return customerRepository.findById(id);
	}


}
