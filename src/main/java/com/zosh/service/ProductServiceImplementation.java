package com.zosh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.zosh.exception.ProductException;
import com.zosh.model.Product;
import com.zosh.repository.CategoryRepository;
import com.zosh.repository.ProductRepository;
import com.zosh.request.CreateProductRequest;
import com.zosh.service.UserService;

@Service
public class ProductServiceImplementation implements ProductService {
    
	@Autowired
	private ProductRepository productRepository;
	
	//@Autowired
	//private UserService userService;
	
	@Autowired
	private CategoryRepository categoryRepository;
	

//	public ProductServiceImplementation(ProductRepository productRepository,UserService userService ,CategoryRepository categoryRepository) {
//		this.productRepository=productRepository;
//		this.categoryRepository=categoryRepository;
//		this.userService=userService;
//	
//		
//
//	}
	
	@Override
	public Product createProduct(CreateProductRequest req) throws ProductException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteProduct(Long productId) throws ProductException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product updateProduct(Long productId, Product product) throws ProductException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product findProductById(Long id) throws ProductException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> findProductByCategory(String category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice,
			Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

}
