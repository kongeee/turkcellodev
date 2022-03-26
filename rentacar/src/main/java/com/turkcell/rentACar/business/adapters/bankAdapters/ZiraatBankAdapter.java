package com.turkcell.rentACar.business.adapters.bankAdapters;

import com.turkcell.rentACar.business.outServices.ZiraatBankManager;
import com.turkcell.rentACar.core.utilities.bankServices.BankService;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ZiraatBankAdapter implements BankService
{
    @Override
    public boolean addPayments(String cardOwnerName, String cardNumber, String cardCVC, String cardDate, double price) 
    {
        ZiraatBankManager bankManager=new ZiraatBankManager();
        return bankManager.addPayments(cardNumber, cardDate, price, cardOwnerName, cardCVC);
    }
}
