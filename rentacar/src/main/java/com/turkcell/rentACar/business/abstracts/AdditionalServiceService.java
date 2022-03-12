package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.AdditionalServiceDto;
import com.turkcell.rentACar.business.dtos.AdditionalServiceListDto;
import com.turkcell.rentACar.business.requests.creates.CreateAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateAdditionalServiceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface AdditionalServiceService 
{
    Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) throws BusinessException;
    Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) throws BusinessException;
    Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) throws BusinessException;
    DataResult<List<AdditionalServiceListDto>> getAll();
    DataResult<AdditionalServiceDto> getById(int additionalServiceId) throws BusinessException;
    DataResult<List<AdditionalServiceListDto>> getAdditionalServicesByIds(List<Integer> additionalServicesIds); 
}
