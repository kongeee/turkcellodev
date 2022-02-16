package com.turkcell.rentacar.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentacar.business.abstracts.BrandService;
import com.turkcell.rentacar.business.dtos.BrandDto;
import com.turkcell.rentacar.business.dtos.BrandListDto;
import com.turkcell.rentacar.business.dtos.ColorListDto;
import com.turkcell.rentacar.business.requests.CreateBrandRequest;
import com.turkcell.rentacar.business.requests.UpdateBrandRequest;
import com.turkcell.rentacar.business.requests.UpdateColorRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.entities.concretes.Brand;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/brands")

public class BrandsController {
	
	private BrandService brandService;
	
	public BrandsController(BrandService brandService) {
		super();
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
	public BrandDto getById(@RequestParam(required = true) int brandId){
		return this.brandService.getById(brandId);
	}
	
	@PostMapping("/update")
	public void update(@RequestBody UpdateBrandRequest updateBrandRequest) throws BusinessException {
		this.brandService.update(updateBrandRequest);
	}
	
	@PostMapping("/deletebyid")
	public void deleteById(@RequestBody int brandId) {
		this.brandService.deleteById(brandId);
	}

	
	
}
