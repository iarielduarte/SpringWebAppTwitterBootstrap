package ar.com.api.ibera.services;

import java.util.List;

import ar.com.api.ibera.models.Product;

public interface ProductService {
	
	public abstract List<Product> getProducts();
	public abstract Product saveProduct(Product product);
	public abstract Product removeProduct(Product product);
	public abstract Product updateProduct(Product product);
}
