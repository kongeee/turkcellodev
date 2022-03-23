package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import com.turkcell.rentACar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentACar.business.abstracts.UserService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.CorporateCustomerDto;
import com.turkcell.rentACar.business.dtos.CorporateCustomerListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteUserRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCorporateCustomerRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CorporateCustomerDao;
import com.turkcell.rentACar.entities.concretes.CorporateCustomer;

import org.springframework.stereotype.Service;

@Service
public class CorporateCustomerManager implements CorporateCustomerService
{
    private CorporateCustomerDao corporateCustomerDao;
    private ModelMapperService modelMapperService;
    private UserService userService;

    public CorporateCustomerManager(CorporateCustomerDao corporateCustomerDao, ModelMapperService modelMapperService, UserService userService) 
    {
        this.corporateCustomerDao = corporateCustomerDao;
        this.modelMapperService = modelMapperService;
        this.userService = userService;
    }

    @Override
    public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException 
    {
        checkIfTaxNumberIsDuplicated(createCorporateCustomerRequest.getTaxNumber());
        
        CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(createCorporateCustomerRequest,CorporateCustomer.class);
		this.corporateCustomerDao.save(corporateCustomer);

		return new SuccessResult(BusinessMessages.CORPORATE_CUSTOMER_ADDED);
    }

    @Override
    public DataResult<List<CorporateCustomerListDto>> getAll() 
    {
        List<CorporateCustomer> result = this.corporateCustomerDao.findAll();
		List<CorporateCustomerListDto> response = result.stream().map(corporateCustomer->this.modelMapperService.forDto().map(corporateCustomer,CorporateCustomerListDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<CorporateCustomerListDto>>(response,BusinessMessages.CORPORATE_CUSTOMER_LISTED);
    }

    @Override
    public DataResult<CorporateCustomerDto> getById(int id) throws BusinessException 
    {
        checkIfExistByCorporateCustomerId(id);
        
        CorporateCustomer corporateCustomer = corporateCustomerDao.getById(id);
        CorporateCustomerDto corporateCustomerDto = this.modelMapperService.forDto().map(corporateCustomer,CorporateCustomerDto.class);
		
		return new SuccessDataResult<CorporateCustomerDto>(corporateCustomerDto,BusinessMessages.CORPORATE_CUSTOMER_GETTED);
    }

    @Override
    public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException 
    {
        checkIfExistByCorporateCustomerId(updateCorporateCustomerRequest.getCorporateCustomerId());
        checkIfTaxNumberIsDuplicated(updateCorporateCustomerRequest.getCorporateCustomerId(), updateCorporateCustomerRequest.getTaxNumber());

        CorporateCustomer corporateCustomer = corporateCustomerDao.getById(updateCorporateCustomerRequest.getCorporateCustomerId());
		corporateCustomer = this.modelMapperService.forRequest().map(updateCorporateCustomerRequest,CorporateCustomer.class);
		corporateCustomer.setUserId(corporateCustomer.getCorporateCustomerId());
        this.corporateCustomerDao.save(corporateCustomer);
		
		return new SuccessResult(BusinessMessages.CORPORATE_CUSTOMER_UPDATED);
    }

    @Override
    public Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) throws BusinessException 
    {
        checkIfExistByCorporateCustomerId(deleteCorporateCustomerRequest.getCorporateCustomerId());

        DeleteUserRequest deleteUserRequest = new DeleteUserRequest(deleteCorporateCustomerRequest.getCorporateCustomerId());

		
        this.userService.delete(deleteUserRequest);
		
		return new SuccessResult(BusinessMessages.CORPORATE_CUSTOMER_DELETED);
    }

    @Override
    public Result checkIfExistByCorporateCustomerId(int corporateCustomerId) throws BusinessException 
    {
		if (!this.corporateCustomerDao.existsById(corporateCustomerId)) {
			throw new BusinessException(BusinessMessages.CORPORATE_CUSTOMER_NOT_FOUND);
		}
        return new SuccessResult();
	}

    private void checkIfTaxNumberIsDuplicated(String taxNumber) throws BusinessException
    {
        if(this.corporateCustomerDao.existsByTaxNumber(taxNumber)){
            throw new BusinessException(BusinessMessages.TAX_NUMBER_ALREADY_EXISTS);
        }
    }

    private void checkIfTaxNumberIsDuplicated(int id, String taxNumber) throws BusinessException
    {
        if(this.corporateCustomerDao.getCorporateCustomerByTaxNumberAndNotEqualToId(id, taxNumber).size()>0){
            throw new BusinessException(BusinessMessages.TAX_NUMBER_ALREADY_EXISTS);
        }
    }
}