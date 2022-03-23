package com.turkcell.rentACar.core.utilities.bankServices;

public interface BankService 
{
    public boolean addPayments(String cardOwnerName,String cardNumber,String cardCVC,String cardDate,double price);    
}
