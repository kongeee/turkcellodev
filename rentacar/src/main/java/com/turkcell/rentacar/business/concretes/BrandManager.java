package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.turkcell.rentacar.business.abstracts.BrandService;
import com.turkcell.rentacar.business.dtos.BrandDto;
import com.turkcell.rentacar.business.dtos.BrandListDto;
import com.turkcell.rentacar.business.requests.CreateBrandRequest;
import com.turkcell.rentacar.business.requests.UpdateBrandRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.dataAccess.abstracts.BrandDao;
import com.turkcell.rentacar.entities.concretes.Brand;
import com.turkcell.rentacar.entities.concretes.Car;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BrandManager implements BrandService {
	@Autowired
	private BrandDao brandDao;
	private ModelMapperService modelMapperService;
	
	
	
	@Override
	public List<BrandListDto> getAll() {
		List<Brand> result = this.brandDao.findAll();
		List<BrandListDto> response = result.stream()
				.map(brand->this.modelMapperService.forDto().map(brand, BrandListDto.class))
				.collect(Collectors.toList());
				
		return response;
	}

	@Override
	public void add(CreateBrandRequest createBrandRequest) throws BusinessException {
		
		
		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);

		checkIfBrandNameIsUnique(brand.getBrandName());
	
			
		this.brandDao.save(brand);
		
	}
	

	@Override
	public BrandDto getById(int brandId) {
		Brand brand = this.brandDao.getById(brandId);
		
		BrandDto response = this.modelMapperService.forDto().map(brand, BrandDto.class);
		
		return response;
	}
	
	
	@Override
	public void update(UpdateBrandRequest updateBrandRequest) throws BusinessException {
		Brand brand = this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);
		
		checkIfBrandNameIsUnique(brand.getBrandName());
		
		this.brandDao.save(brand);
	}

	@Override
	public void deleteById(int brandId) {
		this.brandDao.deleteById(brandId);
		
	}
	
	private boolean checkIfBrandNameIsUnique(String brandName) throws BusinessException {
		
		for(BrandListDto brandElement:this.getAll()) {
			if(brandElement.getBrandName().equals(brandName)) {
				throw new BusinessException("Aynı isimde birden fazla marka olamaz");
			}
		}
		
		return true;
		
	}

	

}
