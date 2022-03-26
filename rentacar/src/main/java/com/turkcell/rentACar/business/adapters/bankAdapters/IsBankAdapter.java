package com.turkcell.rentACar.business.adapters.bankAdapters;

import com.turkcell.rentACar.business.outServices.IsbankManager;
import com.turkcell.rentACar.core.utilities.bankServices.BankService;

import org.springframework.stereotype.Service;

@Service
public class IsBankAdapter implements BankService
{
    @Override
    public boolean addPayments(String cardOwnerName, String cardNumber, String cardCVC, String cardDate, double price) 
    {
        IsbankManager isbankManager= new IsbankManager();
        return isbankManager.addPayments(cardOwnerName, cardNumber, cardCVC, cardDate, price);
    }   
}
