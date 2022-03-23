package com.turkcell.rentACar.api.controllers;

import java.util.List;

import com.turkcell.rentACar.business.abstracts.PaymentService;
import com.turkcell.rentACar.business.dtos.PaymentDto;
import com.turkcell.rentACar.business.dtos.PaymentListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarMaintenanceRequest;
import com.turkcell.rentACar.business.requests.creates.CreatePaymentRequest;
import com.turkcell.rentACar.business.requests.deletes.DeletePaymentRequest;
import com.turkcell.rentACar.business.requests.updates.UpdatePaymentRequest;
import com.turkcell.rentACar.core.utilities.bankServices.BankService;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/PaymentsServices")
public class PaymentsController 
{
    private PaymentService paymentService;

    public PaymentsController(PaymentService paymentService) 
    {
        this.paymentService= paymentService;
    }

    @GetMapping("/getAll")
	public DataResult<List<PaymentListDto>> getAll() 
	{
		return this.paymentService.getAll();
	}

	@GetMapping("/getById")
	public DataResult<PaymentDto> getById(@RequestParam int id) throws BusinessException 
	{
		return this.paymentService.getById(id);
	}

    @PostMapping("/add")
    Result add(@RequestBody CreatePaymentRequest  createPaymentRequest) throws BusinessException
    {
    	return paymentService.add(createPaymentRequest);
    }

	@PutMapping("/update")
	public Result update(@RequestBody UpdatePaymentRequest updateOrderedAdditionalServiceRequest)
			throws BusinessException 
	{
		return this.paymentService.update(updateOrderedAdditionalServiceRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody DeletePaymentRequest deleteOrderedAdditionalServiceRequest)
			throws BusinessException 
	{
		return this.paymentService.delete(deleteOrderedAdditionalServiceRequest);
	}

}
