package com.turkcell.northwind.business.abstracts;

import java.util.List;

import com.turkcell.northwind.business.dtos.ProductListDto;
import com.turkcell.northwind.business.requests.CreateProductRequest;


public interface ProductService {
	List<ProductListDto> getAll();
	void add(CreateProductRequest createProductRequest);
}
