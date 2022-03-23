package com.turkcell.rentACar.business.concretes;


import java.util.List;
import java.util.stream.Collectors;

import com.turkcell.rentACar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentACar.business.abstracts.UserService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.IndividualCustomerDto;
import com.turkcell.rentACar.business.dtos.IndividualCustomerListDto;
import com.turkcell.rentACar.business.requests.creates.CreateIndividualCustomerRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteIndividualCustomerRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteUserRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateIndividualCustomerRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.IndividualCustomerDao;
import com.turkcell.rentACar.entities.concretes.IndividualCustomer;

import org.springframework.stereotype.Service;

@Service
public class IndividualCustomerManager implements IndividualCustomerService
{
    private IndividualCustomerDao individualCustomerDao;
    private ModelMapperService modelMapperService;
    private UserService userService;

    public IndividualCustomerManager(IndividualCustomerDao individualCustomerDao, ModelMapperService modelMapperService, UserService userService) 
    {
        this.individualCustomerDao = individualCustomerDao;
        this.modelMapperService = modelMapperService;
        this.userService = userService;
    }

    @Override
    public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) throws BusinessException 
    {
        checkIfNationalIdentityIsDuplicated(createIndividualCustomerRequest.getNationalIdentity());
        
        IndividualCustomer individualCustomer = this.modelMapperService.forRequest().map(createIndividualCustomerRequest,IndividualCustomer.class);
		this.individualCustomerDao.save(individualCustomer);

		return new SuccessResult(BusinessMessages.INDIVIDUAL_CUSTOMER_ADDED);
    }

    @Override
    public DataResult<List<IndividualCustomerListDto>> getAll() 
    {
        List<IndividualCustomer> result = this.individualCustomerDao.findAll();
		List<IndividualCustomerListDto> response = result.stream().map(individualCustomer->this.modelMapperService.forDto().map(individualCustomer,IndividualCustomerListDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<IndividualCustomerListDto>>(response,BusinessMessages.INDIVIDUAL_CUSTOMER_LISTED);
    }

    @Override
    public DataResult<IndividualCustomerDto> getById(int id) throws BusinessException 
    {
        checkIfExistByIndividualCustomerId(id);
        
        IndividualCustomer individualCustomer = individualCustomerDao.getById(id);

        IndividualCustomerDto individualCustomerDto = this.modelMapperService.forDto().map(individualCustomer,IndividualCustomerDto.class);
		
		return new SuccessDataResult<IndividualCustomerDto>(individualCustomerDto, BusinessMessages.INDIVIDUAL_CUSTOMER_GETTED);
    }

    @Override
    public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws BusinessException 
    {
        checkIfExistByIndividualCustomerId(updateIndividualCustomerRequest.getIndividualCustomerId());
        checkIfNationalIdentityIsDuplicated(updateIndividualCustomerRequest.getIndividualCustomerId(), updateIndividualCustomerRequest.getNationalIdentity()); 

        IndividualCustomer individualCustomer = individualCustomerDao.getById(updateIndividualCustomerRequest.getIndividualCustomerId());
		individualCustomer = this.modelMapperService.forRequest().map(updateIndividualCustomerRequest,IndividualCustomer.class);
		
        individualCustomer.setUserId(individualCustomer.getIndividualCustomerId());
        
        this.individualCustomerDao.save(individualCustomer);
		
		return new SuccessResult(BusinessMessages.INDIVIDUAL_CUSTOMER_UPDATED);
    }

    @Override
    public Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) throws BusinessException 
    {   
        
        checkIfExistByIndividualCustomerId(deleteIndividualCustomerRequest.getIndividualCustomerId());

        DeleteUserRequest deleteUserRequest = new DeleteUserRequest(deleteIndividualCustomerRequest.getIndividualCustomerId());
		
        this.userService.delete(deleteUserRequest);
		
		return new SuccessResult(BusinessMessages.INDIVIDUAL_CUSTOMER_DELETED);
    }

    @Override
    public Result checkIfExistByIndividualCustomerId(int individualCustomerId) throws BusinessException 
    {
		if (!this.individualCustomerDao.existsById(individualCustomerId)) {
			throw new BusinessException(BusinessMessages.INDIVIDUAL_CUSTOMER_ALREADY_EXISTS);
		}
        return new SuccessResult();
	}

    private void checkIfNationalIdentityIsDuplicated(String nationalIdentity) throws BusinessException
    {
        if(this.individualCustomerDao.existsByNationalIdentity(nationalIdentity)){
            throw new BusinessException(BusinessMessages.NATIONAL_IDENTITY_ALREADY_EXISTS);
        }
    }

    private void checkIfNationalIdentityIsDuplicated(int id, String nationalIdentity) throws BusinessException
    {
        if(this.individualCustomerDao.getIndividualCustomerByNationalIdentityAndNotEqualToId(id, nationalIdentity).size()>0){
            throw new BusinessException(BusinessMessages.NATIONAL_IDENTITY_ALREADY_EXISTS);
        }
    }
}