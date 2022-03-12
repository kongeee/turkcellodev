package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.CorporateCustomerDto;
import com.turkcell.rentACar.business.dtos.CorporateCustomerListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCorporateCustomerRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface CorporateCustomerService {
   
    DataResult<List<CorporateCustomerListDto>> getAll();
    Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException;
    DataResult<CorporateCustomerDto> getById(int id) throws BusinessException;
	Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException;
	Result delete(DeleteCorporateCustomerRequest DeleteCorporateCustomerRequest) throws BusinessException;
}