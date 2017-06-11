package ar.com.api.ibera.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.com.api.ibera.models.Product;
import ar.com.api.ibera.services.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {

	private static final Log LOGGER = LogFactory.getLog(ProductController.class);
	
	private static final String PRODUCT_VIEW = "product";
	private static final String ADD_VIEW = "addproduct";
	
	@Autowired
	@Qualifier("productService")
	private ProductService productService;
	
	@GetMapping("/list")
	public ModelAndView products(){
		ModelAndView mv = new ModelAndView(PRODUCT_VIEW);
		mv.addObject("products", productService.getProducts());
		return mv;
	}
	
	@GetMapping("/new")
	public ModelAndView newProduct(){
		ModelAndView mv = new ModelAndView(ADD_VIEW);
		mv.addObject("product", new Product());
		return mv;
	}
	
	@PostMapping("/add")
	public String addProduct(@ModelAttribute("product") Product product){
		productService.saveProduct(product);
		LOGGER.info("New product "+ product.toString());
		return "redirect:/product/list";
	}
}
