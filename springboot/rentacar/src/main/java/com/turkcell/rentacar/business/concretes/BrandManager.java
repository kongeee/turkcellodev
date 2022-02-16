package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.BrandService;
import com.turkcell.rentacar.business.dtos.BrandListDto;
import com.turkcell.rentacar.business.dtos.GetBrandDto;
import com.turkcell.rentacar.business.requests.CreateBrandRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.dataAccess.abstracts.BrandDao;
import com.turkcell.rentacar.entities.concretes.Brand;

@Service
public class BrandManager implements BrandService {
	
	private BrandDao brandDao;
	private ModelMapperService modelMapperService;
	
	@Autowired
	public BrandManager(BrandDao brandDao, ModelMapperService modelMapperService) {
		
		this.brandDao = brandDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public List<BrandListDto> getAll() {
		List<Brand> result = brandDao.findAll();
		
		List<BrandListDto> response = result.stream()
				.map(brand -> this.modelMapperService.forDto().map(brand, BrandListDto.class))
				.collect(Collectors.toList());
		
		return response;
	}

	@Override
	public void add(CreateBrandRequest createBrandRequest) throws BusinessException {
		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);
		CheckIfBrandNameUnique(brand.getName());
		
		this.brandDao.save(brand);
		
	}

	@Override
	public GetBrandDto getById(int id) {
		Brand result = brandDao.getById(id);
		
		GetBrandDto response = this.modelMapperService.forDto()
							   .map(result, GetBrandDto.class);
		
		return response;
	}
	
	
	private boolean CheckIfBrandNameUnique(String brandName) throws BusinessException {
		
		for(BrandListDto brand : this.getAll()) {
			if(brand.getName().equals(brandName)) {
				throw new BusinessException("Aynı isme sahip birden fazla marka olamaz!");
			}
		}
		return true;
	}

}
