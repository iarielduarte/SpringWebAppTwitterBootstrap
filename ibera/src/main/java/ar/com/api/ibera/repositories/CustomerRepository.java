package ar.com.api.ibera.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.api.ibera.models.Customer;

@Repository("customerRepository")
public interface CustomerRepository extends JpaRepository<Customer, Serializable>{

	public abstract Customer findById(Integer id);
	
}
