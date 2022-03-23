package com.turkcell.rentACar.business.adapters.bankAdapters;

import com.turkcell.rentACar.core.utilities.bankServices.ZiraatBankManager;

import org.springframework.stereotype.Service;

@Service
public class ZiraatBankAdapter implements ZiraatBankService
{
    @Override
    public boolean addPayments(String cardOwnerName, String cardNumber, String cardCVC, String cardDate, double price) 
    {
        ZiraatBankManager bankManager=new ZiraatBankManager();
        return bankManager.addPayments(cardNumber, cardDate, price, cardOwnerName, cardCVC);
    }
}
