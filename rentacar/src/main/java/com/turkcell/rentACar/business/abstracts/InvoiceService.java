package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.InvoiceDto;
import com.turkcell.rentACar.business.dtos.InvoiceListDto;
import com.turkcell.rentACar.business.requests.creates.CreateInvoiceRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteInvoiceRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateInvoiceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

import java.util.List;

public interface InvoiceService 
{
	DataResult<List<InvoiceListDto>> getAll();
	DataResult<InvoiceDto> add(CreateInvoiceRequest createInvoiceRequest) throws BusinessException;
	DataResult<InvoiceDto> getById(int id) throws BusinessException;
	Result update(UpdateInvoiceRequest updateInvoiceRequest) throws BusinessException;
	Result delete(DeleteInvoiceRequest DeleteInvoiceRequest) throws BusinessException;

	Result checkIfExistByInvoiceId(int invoiceId) throws BusinessException;
}