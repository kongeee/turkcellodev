package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.CustomerService;
import com.turkcell.rentACar.business.constants.messages.AddedMessages;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CustomerDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerManager implements CustomerService
{
    private CustomerDao customerDao;
    
    @Autowired
    public CustomerManager(CustomerDao customerDao) 
    {
        this.customerDao = customerDao;
    }

    @Override
    public Result checkIfExistByCustomerId(int customerId) throws BusinessException 
    {
        if(!customerDao.existsById(customerId))
        {
            throw new BusinessException(BusinessMessages.CUSTOMER_ALREADY_EXISTS);
        }

        return new SuccessResult();
    }
    
}
