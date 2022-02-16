package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.business.dtos.BrandDto;
import com.turkcell.rentacar.business.dtos.BrandListDto;
import com.turkcell.rentacar.business.requests.CreateBrandRequest;
import com.turkcell.rentacar.business.requests.UpdateBrandRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;


public interface BrandService {
	List<BrandListDto> getAll();
	void add(CreateBrandRequest createBrandRequest) throws BusinessException;
	BrandDto getById(int id);
	
	void update(UpdateBrandRequest updateBrandRequest) throws BusinessException;
	void deleteById(int brandId);
}
