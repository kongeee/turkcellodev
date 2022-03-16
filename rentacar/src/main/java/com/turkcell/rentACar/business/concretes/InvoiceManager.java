package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.InvoiceService;
import com.turkcell.rentACar.business.dtos.InvoiceDto;
import com.turkcell.rentACar.business.dtos.InvoiceListDto;
import com.turkcell.rentACar.business.requests.creates.CreateInvoiceRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteInvoiceRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateInvoiceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.*;
import com.turkcell.rentACar.dataAccess.abstracts.InvoiceDao;
import com.turkcell.rentACar.entities.concretes.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceManager implements InvoiceService 
{
	private InvoiceDao invoiceDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public InvoiceManager(InvoiceDao invoiceDao, ModelMapperService modelMapperService) 
    {
		this.invoiceDao = invoiceDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<InvoiceListDto>> getAll() 
    {
		List<Invoice> result = this.invoiceDao.findAll();
		List<InvoiceListDto> response = result.stream()
				.map(invoice -> this.modelMapperService.forDto().map(invoice, InvoiceListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<InvoiceListDto>>(response, "Invoices Listed Successfully");
	}

	@Override
	public Result add(CreateInvoiceRequest createInvoiceRequest) throws BusinessException 
    {
		Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);
		invoice.setInvoiceId(0);
		this.invoiceDao.save(invoice);

		return new SuccessResult("Invoice Added Successfully");
	}

	@Override
	public DataResult<InvoiceDto> getById(int id) throws BusinessException 
    {
		checkIfExistByInvoiceId(id);

		Invoice invoice = invoiceDao.getById(id);
		InvoiceDto invoiceDto = this.modelMapperService.forDto().map(invoice, InvoiceDto.class);

		return new SuccessDataResult<InvoiceDto>(invoiceDto, "Invoice Listed Successfully");
	}

	@Override
	public Result update(UpdateInvoiceRequest updateInvoiceRequest) throws BusinessException 
    {
		Invoice invoice= this.invoiceDao.getByCarRental_CarRentalId(updateInvoiceRequest.getCarRentalId());

		checkIfExistByInvoiceId(invoice.getInvoiceId());
		
		Invoice invoiceUpdate = this.modelMapperService.forRequest().map(updateInvoiceRequest, Invoice.class);
		invoiceUpdate.setInvoiceId(invoice.getInvoiceId());

		this.invoiceDao.save(invoiceUpdate);

		return new SuccessResult("Invoice Updated Succesfully");
	}

	@Override
	public Result delete(DeleteInvoiceRequest deleteInvoiceRequest) throws BusinessException 
    {
		checkIfExistByInvoiceId(deleteInvoiceRequest.getInvoiceId());

		Invoice invoice = this.modelMapperService.forRequest().map(deleteInvoiceRequest, Invoice.class);
		this.invoiceDao.delete(invoice);

		return new SuccessResult("Invoice Deleted Succesfully");
	}

	private void checkIfExistByInvoiceId(int invoiceId) throws BusinessException 
    {
		if (!this.invoiceDao.existsById(invoiceId)) {
			throw new BusinessException("Invoice not found");
		}
	}
}