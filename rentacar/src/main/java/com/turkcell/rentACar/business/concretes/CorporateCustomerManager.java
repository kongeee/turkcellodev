package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import com.turkcell.rentACar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentACar.business.dtos.CorporateCustomerDto;
import com.turkcell.rentACar.business.dtos.CorporateCustomerListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCorporateCustomerRequest;
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
public class CorporateCustomerManager implements CorporateCustomerService{

    private CorporateCustomerDao corporateCustomerDao;
    private ModelMapperService modelMapperService;
    public CorporateCustomerManager(CorporateCustomerDao corporateCustomerDao, ModelMapperService modelMapperService) {
        this.corporateCustomerDao = corporateCustomerDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException {

        checkIfTaxNumberIsDuplicated(createCorporateCustomerRequest.getTaxNumber());
        
        CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(createCorporateCustomerRequest,CorporateCustomer.class);
		this.corporateCustomerDao.save(corporateCustomer);

		return new SuccessResult("CorporateCustomer Added Successfully");


    }

    @Override
    public DataResult<List<CorporateCustomerListDto>> getAll() {
        List<CorporateCustomer> result = this.corporateCustomerDao.findAll();
		List<CorporateCustomerListDto> response = result.stream().map(corporateCustomer->this.modelMapperService.forDto().map(corporateCustomer,CorporateCustomerListDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<CorporateCustomerListDto>>(response,"CorporateCustomers Listed Successfully");
    }

    @Override
    public DataResult<CorporateCustomerDto> getById(int id) throws BusinessException {

        checkIfExistByCorporateCustomerId(id);
        
        CorporateCustomer corporateCustomer = corporateCustomerDao.getById(id);
        CorporateCustomerDto corporateCustomerDto = this.modelMapperService.forDto().map(corporateCustomer,CorporateCustomerDto.class);
		
		return new SuccessDataResult<CorporateCustomerDto>(corporateCustomerDto,"CorporateCustomer Listed Successfully");
    }

    @Override
    public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException {

        checkIfExistByCorporateCustomerId(updateCorporateCustomerRequest.getCorporateCustomerId());
        //checkIfTaxNumberIsDuplicated

        CorporateCustomer corporateCustomer = corporateCustomerDao.getById(updateCorporateCustomerRequest.getCorporateCustomerId());
		corporateCustomer = this.modelMapperService.forRequest().map(updateCorporateCustomerRequest,CorporateCustomer.class);
		
        this.corporateCustomerDao.save(corporateCustomer);
		
		return new SuccessResult("CorporateCustomer Updated Succesfully");
    }

    @Override
    public Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) throws BusinessException {

        checkIfExistByCorporateCustomerId(deleteCorporateCustomerRequest.getCorporateCustomerId());

        CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(deleteCorporateCustomerRequest,CorporateCustomer.class);

		this.corporateCustomerDao.delete(corporateCustomer);
		
		return new SuccessResult("CorporateCustomer Deleted Succesfully");
    }


    private void checkIfExistByCorporateCustomerId(int corporateCustomerId) throws BusinessException {
		if (!this.corporateCustomerDao.existsById(corporateCustomerId)) {
			throw new BusinessException("CorporateCustomer not found");
		}
	}

    private void checkIfTaxNumberIsDuplicated(String taxNumber) throws BusinessException{
        if(this.corporateCustomerDao.existsByTaxNumber(taxNumber)){
            throw new BusinessException("National Identity already exists");
        }
    }
    
}