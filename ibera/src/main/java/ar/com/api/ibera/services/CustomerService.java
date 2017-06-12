package ar.com.api.ibera.services;

import java.util.List;

import ar.com.api.ibera.models.Customer;

public interface CustomerService {
	public abstract Customer saveCustomer(Customer customer);
	public abstract Customer removeCustomer(Customer customer);
	public abstract Customer updateCustomer(Customer customer);
	public abstract List<Customer> getCustomers();
	public abstract Customer findCustomerById(Integer id);
}
