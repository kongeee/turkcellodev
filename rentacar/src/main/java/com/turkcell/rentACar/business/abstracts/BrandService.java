package com.turkcell.rentACar.business.abstracts;


import com.turkcell.rentACar.business.dtos.BrandDto;
import com.turkcell.rentACar.business.dtos.BrandListDto;
import com.turkcell.rentACar.business.requests.creates.CreateBrandRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteBrandRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateBrandRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

import java.util.List;

public interface BrandService {
    DataResult<List<BrandListDto>> getAll();
    Result add(CreateBrandRequest createBrandRequest) throws BusinessException;
    DataResult<BrandDto> getById(int id);
    Result update(UpdateBrandRequest updateBrandRequest) throws BusinessException;
    Result delete(DeleteBrandRequest deleteBrandRequest) throws BusinessException;
}
