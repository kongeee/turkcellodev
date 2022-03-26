package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.PosService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.core.utilities.bankServices.BankService;
import com.turkcell.rentACar.core.utilities.bankServices.BankInformation;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PosManager implements PosService
{
    private BankService bankService;

    @Autowired
    public PosManager(BankService bankService) 
    {
       this.bankService = bankService;
    }

    @Override
    public Result pay(BankInformation posInformation, double price) throws BusinessException 
    {
        var result = bankService.addPayments(posInformation.getCardOwnerName(), posInformation.getCardNumber(), posInformation.getCardCVC(), posInformation.getCardDate(), price);

        if(result)
        {
            return new SuccessResult();
        }

        throw new BusinessException(BusinessMessages.PAYMENT_FAILED);
    }
}
