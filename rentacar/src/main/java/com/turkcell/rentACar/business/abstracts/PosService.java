package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.core.utilities.bankServices.BankInformation;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface PosService 
{
    public Result pay(BankInformation posInformation, double price) throws BusinessException;
}
