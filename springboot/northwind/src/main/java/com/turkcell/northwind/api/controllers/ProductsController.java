package com.turkcell.northwind.api.controllers;

import java.util.List;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.northwind.business.abstracts.ProductService;
import com.turkcell.northwind.business.dtos.ProductListDto;
import com.turkcell.northwind.business.requests.CreateProductRequest;




@RestController
@RequestMapping("/api/products") //endpoint
public class ProductsController {
	private ProductService productService;
	
	
	public ProductsController(ProductService productService) {
		
		this.productService = productService;
	}
	
	@GetMapping("/getall")
	public List<ProductListDto> getAll(){
		return this.productService.getAll();
	}
	
	@PostMapping("/add")
	public void add(@RequestBody CreateProductRequest createProductRequest) { //RequestBody -> 
		this.productService.add(createProductRequest);
	}
}
