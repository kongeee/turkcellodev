package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.CustomerCardDto;
import com.turkcell.rentACar.business.dtos.CustomerCardListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCustomerCardRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCustomerCardRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCustomerCardRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface CustomerCardService {

    DataResult<List<CustomerCardListDto>> getAll();
	Result add(CreateCustomerCardRequest createCustomerCardRequest) throws BusinessException;
	DataResult<CustomerCardDto> getById(int id) throws BusinessException;
	Result update(UpdateCustomerCardRequest updateCustomerCardRequest) throws BusinessException;
	Result delete(DeleteCustomerCardRequest DeleteCustomerCardRequest) throws BusinessException;
    
}
