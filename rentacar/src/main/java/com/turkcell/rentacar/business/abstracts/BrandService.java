package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.business.dtos.BrandByIdDto;
import com.turkcell.rentacar.business.dtos.BrandListDto;
import com.turkcell.rentacar.business.requests.CreateBrandRequest;
import com.turkcell.rentacar.business.requests.UpdateBrandRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;


public interface BrandService {
	DataResult<List<BrandListDto>> getAll();

    Result add(CreateBrandRequest createBrandRequest) throws BusinessException;

    DataResult<BrandByIdDto> getById(int id);

    Result update(UpdateBrandRequest updateBrandRequest) throws BusinessException;

    Result deleteById(int brandId);
}
