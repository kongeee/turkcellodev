package com.turkcell.northwind.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.northwind.business.abstracts.ProductService;
import com.turkcell.northwind.business.dtos.ProductListDto;
import com.turkcell.northwind.business.requests.CreateProductRequest;
import com.turkcell.northwind.core.utilities.mapping.ModelMapperService;
import com.turkcell.northwind.dataAccess.abstracts.ProductDao;
import com.turkcell.northwind.entities.concretes.Product;

@Service
public class ProductManager implements ProductService {
	
	private ProductDao productDao;
	private ModelMapperService modelMapperService;
	
	//aşağıdaki satır productdao nun concrete bir sınıfını, productdao ya bağlar
	@Autowired 
	public ProductManager(ProductDao productDao, ModelMapperService modelMapperService) {
		
		this.productDao = productDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public List<ProductListDto> getAll() {
		
		List<Product> result = this.productDao.findAll();
		
		List<ProductListDto> response = result.stream()
				.map(product->this.modelMapperService.forDto().map(product, ProductListDto.class))
				.collect(Collectors.toList());
		
		return response;
	}

	@Override
	public void add(CreateProductRequest createProductRequest) {
		Product product = this.modelMapperService.forRequest()
				.map(createProductRequest, Product.class);
		this.productDao.save(product);
		
	}

}
