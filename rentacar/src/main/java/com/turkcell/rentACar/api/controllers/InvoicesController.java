package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.InvoiceService;
import com.turkcell.rentACar.business.dtos.InvoiceDto;
import com.turkcell.rentACar.business.dtos.InvoiceListDto;
import com.turkcell.rentACar.business.requests.creates.CreateInvoiceRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteInvoiceRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateInvoiceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoicesController {
	private InvoiceService invoiceService;

	public InvoicesController(InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}

	@GetMapping("/getAll")
	DataResult<List<InvoiceListDto>> getAll() {
		return this.invoiceService.getAll();
	}

	@GetMapping("/getById")
	public DataResult<InvoiceDto> getById(@RequestParam int id) throws BusinessException {
		return this.invoiceService.getById(id);
	}

	@PostMapping("/add")
	public Result add(@RequestBody CreateInvoiceRequest createInvoiceRequest) throws BusinessException {
		return this.invoiceService.add(createInvoiceRequest);
	}

	@PutMapping("/update")
	public Result update(@RequestBody UpdateInvoiceRequest updateInvoiceRequest) throws BusinessException {
		return this.invoiceService.update(updateInvoiceRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody DeleteInvoiceRequest deleteInvoiceRequest) throws BusinessException {
		return this.invoiceService.delete(deleteInvoiceRequest);
	}

}
