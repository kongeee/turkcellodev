package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import com.turkcell.rentACar.business.abstracts.CustomerCardService;
import com.turkcell.rentACar.business.abstracts.CustomerService;
import com.turkcell.rentACar.business.dtos.CustomerCardDto;
import com.turkcell.rentACar.business.dtos.CustomerCardListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCustomerCardRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCustomerCardRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCustomerCardRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CustomerCardDao;
import com.turkcell.rentACar.entities.concretes.CustomerCard;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerCardManager implements CustomerCardService {

    private CustomerCardDao customerCardDao;
	private ModelMapperService modelMapperService;
	private CustomerService customerService;
   

	@Autowired
	public CustomerCardManager(CustomerCardDao customerCardDao,ModelMapperService modelMapperService, CustomerService customerService) 
	{
		this.customerCardDao = customerCardDao;
		this.modelMapperService = modelMapperService;
		this.customerService = customerService;
	}

	@Override
	public DataResult<List<CustomerCardListDto>> getAll() 
	{
		List<CustomerCard> result = this.customerCardDao.findAll();
		List<CustomerCardListDto> response = result.stream().map(customerCard->this.modelMapperService.forDto().map(customerCard,CustomerCardListDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<CustomerCardListDto>>();
	}

	@Override
	public Result add(CreateCustomerCardRequest createCustomerCardRequest) throws BusinessException 
	{
		
		checkIfExistsByCardNumber(createCustomerCardRequest.getCardNumber());
		checkIfExistsByCustomerId(createCustomerCardRequest.getCustomerId());

		CustomerCard customerCard = this.modelMapperService.forRequest().map(createCustomerCardRequest,CustomerCard.class);
		this.customerCardDao.save(customerCard);

		return new SuccessResult();
	}

	@Override
	public DataResult<CustomerCardDto> getById(int id) throws BusinessException 
	{

		checkIfExistsByCardId(id);

		CustomerCard customerCard = customerCardDao.getById(id);
		CustomerCardDto customerCardDto = this.modelMapperService.forDto().map(customerCard,CustomerCardDto.class);

		return new SuccessDataResult<CustomerCardDto>(customerCardDto,BusinessMessages.BRAND_GETTED);
	}

	@Override
	public Result update(UpdateCustomerCardRequest updateCustomerCardRequest) throws BusinessException 
	{
		checkIfExistsByCardId(updateCustomerCardRequest.getCustomerCardId());
		checkIfExistsByCardNumber(updateCustomerCardRequest.getCardNumber(), updateCustomerCardRequest.getCustomerCardId());
		checkIfExistsByCustomerId(updateCustomerCardRequest.getCustomerId());

		CustomerCard customerCard = this.customerCardDao.getById(updateCustomerCardRequest.getCustomerCardId());
		customerCard = this.modelMapperService.forRequest().map(updateCustomerCardRequest,CustomerCard.class);
		this.customerCardDao.save(customerCard);

		return new SuccessResult();
	}

	@Override
	public Result delete(DeleteCustomerCardRequest deleteCustomerCardRequest) throws BusinessException
	{
		checkIfExistsByCardId(deleteCustomerCardRequest.getCustomerCardId());

		CustomerCard customerCard = this.modelMapperService.forRequest().map(deleteCustomerCardRequest,CustomerCard.class);
		this.customerCardDao.delete(customerCard);

		return new SuccessResult();
	}

    private void checkIfExistsByCardNumber(String cardNumber) throws BusinessException{
        if(this.customerCardDao.existsByCardNumber(cardNumber)){
            throw new BusinessException("message");
        }
    }

    private void checkIfExistsByCardNumber(String cardNumber, int customerCardId) throws BusinessException{
        if(this.customerCardDao.getCustomerCardByCardNumberAndNotEqualToId(customerCardId, cardNumber).size()>0){
            throw new BusinessException("message");
        }
    }

    private void checkIfExistsByCardId(int customerCardId) throws BusinessException{
        if(!this.customerCardDao.existsById(customerCardId)){
            throw new BusinessException("message");
        }
    }

    private void checkIfExistsByCustomerId(int customerId){

		this.customerService.checkIfExistByCustomerId(customerId);
        
    }

    

}
