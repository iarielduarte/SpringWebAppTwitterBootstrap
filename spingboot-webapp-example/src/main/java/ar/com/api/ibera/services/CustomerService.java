package ar.com.api.ibera.services;

import java.util.List;

import ar.com.api.ibera.models.Customer;

public interface CustomerService {

	public abstract List<Customer> getCustomersList();
}
