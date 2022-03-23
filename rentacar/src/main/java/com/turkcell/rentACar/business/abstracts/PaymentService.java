package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.PaymentDto;
import com.turkcell.rentACar.business.dtos.PaymentListDto;
import com.turkcell.rentACar.business.requests.creates.CreatePaymentRequest;
import com.turkcell.rentACar.business.requests.deletes.DeletePaymentRequest;
import com.turkcell.rentACar.business.requests.updates.UpdatePaymentRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface PaymentService 
{
    DataResult<List<PaymentListDto>> getAll();
    Result add(CreatePaymentRequest createPaymentRequest) throws BusinessException;
    Result update(UpdatePaymentRequest updatePaymentRequest) throws BusinessException;
    Result delete(DeletePaymentRequest deletePaymentRequest) throws BusinessException;
    DataResult<PaymentDto> getById(int id) throws BusinessException;
}
