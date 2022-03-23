package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.PosService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.core.utilities.bankServices.BankService;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.posService.PosInformation;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;

import org.springframework.stereotype.Service;

@Service
public class PosManager implements PosService
{
    @Override
    public Result pay(PosInformation posInformation, double price, BankService bankService) throws BusinessException 
    {
        var result = bankService.addPayments(posInformation.getCardOwnerName(), posInformation.getCardNumber(), posInformation.getCardCVC(), posInformation.getCardDate(), price);

        if(result)
        {
            return new SuccessResult();
        }

        throw new BusinessException(BusinessMessages.PAYMENT_FAILED);
    }
}
