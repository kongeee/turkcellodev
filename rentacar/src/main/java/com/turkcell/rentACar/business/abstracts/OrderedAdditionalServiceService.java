package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.OrderedAdditionalServiceDto;
import com.turkcell.rentACar.business.dtos.OrderedAdditionalServiceListDto;
import com.turkcell.rentACar.business.requests.creates.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface OrderedAdditionalServiceService {
    Result add(CreateOrderedAdditionalServiceRequest createAdditionalServiceRequest);
    Result update(UpdateOrderedAdditionalServiceRequest updateAdditionalServiceRequest);
    Result delete(DeleteOrderedAdditionalServiceRequest deleteAdditionalServiceRequest);
    DataResult<List<OrderedAdditionalServiceListDto>> getAll();
    DataResult<OrderedAdditionalServiceDto> getById(int additionalServiceId);
}
