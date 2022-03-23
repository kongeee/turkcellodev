package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.core.utilities.bankServices.BankService;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.posService.PosInformation;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface PosService 
{
    public Result pay(PosInformation posInformation, double price, BankService bankService) throws BusinessException;
}
