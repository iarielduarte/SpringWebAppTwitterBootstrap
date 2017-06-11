package ar.com.api.ibera.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.api.ibera.models.Product;

@Repository("productRepository")
public interface ProductRepository extends JpaRepository<Product, Serializable>{

	
}
