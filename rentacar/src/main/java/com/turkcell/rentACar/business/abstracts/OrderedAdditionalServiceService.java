package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.OrderedAdditionalServiceDto;
import com.turkcell.rentACar.business.dtos.OrderedAdditionalServiceListDto;
import com.turkcell.rentACar.business.requests.creates.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface OrderedAdditionalServiceService {
    Result add(CreateOrderedAdditionalServiceRequest createAdditionalServiceRequest) throws BusinessException;
    Result update(UpdateOrderedAdditionalServiceRequest updateAdditionalServiceRequest) throws BusinessException;
    Result delete(DeleteOrderedAdditionalServiceRequest deleteAdditionalServiceRequest) throws BusinessException;
    Result deleteAllByCarRentelId(int carRentelId) throws BusinessException;
    DataResult<List<OrderedAdditionalServiceListDto>> getAll();
    DataResult<OrderedAdditionalServiceDto> getById(int additionalServiceId) throws BusinessException;
}
