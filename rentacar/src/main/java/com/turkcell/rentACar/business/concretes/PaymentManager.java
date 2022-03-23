package com.turkcell.rentACar.business.concretes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.turkcell.rentACar.business.abstracts.CustomerService;
import com.turkcell.rentACar.business.abstracts.InvoiceService;
import com.turkcell.rentACar.business.abstracts.PaymentService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dependencyResolvers.BankServiceModule;
import com.turkcell.rentACar.business.dtos.PaymentDto;
import com.turkcell.rentACar.business.dtos.PaymentListDto;
import com.turkcell.rentACar.business.requests.creates.CreatePaymentRequest;
import com.turkcell.rentACar.business.requests.deletes.DeletePaymentRequest;
import com.turkcell.rentACar.business.requests.updates.UpdatePaymentRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.PaymentDao;
import com.turkcell.rentACar.entities.concretes.Payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentManager implements PaymentService
{ 
    private PaymentDao paymentDao;
    private ModelMapperService modelMapperService;

    private InvoiceService invoiceService;
    private CustomerService customerService;

    @Autowired
    public PaymentManager( ModelMapperService modelMapperService, BankServiceModule bankServiceModule,PaymentDao paymentDao, InvoiceService invoiceService, CustomerService customerService) 
    {
        this.paymentDao = paymentDao;
        this.modelMapperService = modelMapperService;

        this.invoiceService= invoiceService;
        this.customerService= customerService;
    }

    @Override
    public DataResult<List<PaymentListDto>> getAll() 
    {
        List<Payment> result = this.paymentDao.findAll();
	    	List<PaymentListDto> response = result.stream().map(payment->this.modelMapperService.forDto().map(payment,PaymentListDto.class)).collect(Collectors.toList());
		
		    return new SuccessDataResult<List<PaymentListDto>>(response,BusinessMessages.PAYMENT_LISTED);
    }

    @Override
    public Result add(CreatePaymentRequest createPaymentRequest) throws BusinessException 
    {
        checkIfExistByInvoiceId(createPaymentRequest.getInvoiceId());
        checkIfExistByCustomerId(createPaymentRequest.getCustomerId());

        Payment payment = this.modelMapperService.forRequest().map(createPaymentRequest,Payment.class);

        payment.getCustomer().setUserId(createPaymentRequest.getCustomerId());
        payment.setPaymentDate(LocalDate.now());

        this.paymentDao.save(payment); 
        
        return new SuccessResult(BusinessMessages.PAYMENT_ADDED);
    }


    @Override
    public Result update(UpdatePaymentRequest updatePaymentRequest) throws BusinessException 
    {
        checkIfExistByCarId(updatePaymentRequest.getPaymentId());
        checkIfExistByInvoiceId(updatePaymentRequest.getInvoiceId());
        checkIfExistByCustomerId(updatePaymentRequest.getCustomerId());

        Payment payment = this.modelMapperService.forRequest().map(updatePaymentRequest,Payment.class);
        
        this.paymentDao.save(payment); 
      
		    return new SuccessResult(BusinessMessages.PAYMENT_UPDATED);
    }

    @Override
    public Result delete(DeletePaymentRequest deletePaymentRequest) throws BusinessException 
    {
        checkIfExistByCarId(deletePaymentRequest.getPaymentId());

        this.paymentDao.deleteById(deletePaymentRequest.getPaymentId()); 
        
        return new SuccessResult(BusinessMessages.PAYMENT_DELETED);
    }

    @Override
    public DataResult<PaymentDto> getById(int id) throws BusinessException 
    {
        var result=  this.paymentDao.getById(id); 
        PaymentDto response = this.modelMapperService.forDto().map(result,PaymentDto.class);
        
        return new SuccessDataResult<PaymentDto>(response,BusinessMessages.PAYMENT_GETTED);
    }
    
    private void checkIfExistByCarId(int paymentId) throws BusinessException 
    {
      if(!this.paymentDao.existsById(paymentId)) 
      {
        throw new BusinessException(BusinessMessages.PAYMENT_NOT_FOUND);
      }
    }

    private void checkIfExistByInvoiceId(int invoiceId) throws BusinessException
    {
      this.invoiceService.checkIfExistByInvoiceId(invoiceId);
    } 

    private void checkIfExistByCustomerId(int customerId) throws BusinessException
    {
      this.customerService.checkIfExistByCustomerId(customerId);
    } 
}
