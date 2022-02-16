package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.business.dtos.BrandListDto;
import com.turkcell.rentacar.business.dtos.GetBrandDto;
import com.turkcell.rentacar.business.requests.CreateBrandRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;


public interface BrandService {
	List<BrandListDto> getAll();
	void add (CreateBrandRequest createBrandRequest) throws BusinessException;
	GetBrandDto getById(int id);
}
