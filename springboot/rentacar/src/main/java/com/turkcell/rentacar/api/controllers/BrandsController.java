package com.turkcell.rentacar.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentacar.business.abstracts.BrandService;
import com.turkcell.rentacar.business.dtos.BrandListDto;
import com.turkcell.rentacar.business.dtos.GetBrandDto;
import com.turkcell.rentacar.business.requests.CreateBrandRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.entities.concretes.Brand;

@RestController
@RequestMapping("/api/brands")
public class BrandsController {
	
	@Autowired
	private BrandService brandService;
	
	
	public BrandsController(BrandService brandService) {
		
		this.brandService = brandService;
	}
	
	@GetMapping("/getall")
	public List<BrandListDto> getAll(){
		return this.brandService.getAll();
	}
	
	@PostMapping("/add")
	public void add(@RequestBody CreateBrandRequest createBrandRequest) throws BusinessException {
		this.brandService.add(createBrandRequest);
	}
	
	@GetMapping("/getbyid")
	public GetBrandDto getById(int id) {
		return this.brandService.getById(id);
	}
	
}
