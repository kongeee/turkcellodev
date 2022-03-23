package com.turkcell.rentACar.business.dependencyResolvers;

import java.util.HashMap;

import com.turkcell.rentACar.business.adapters.bankAdapters.IsBankService;
import com.turkcell.rentACar.business.adapters.bankAdapters.ZiraatBankService;
import com.turkcell.rentACar.core.utilities.bankServices.BankService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankServiceModule 
{
    private ZiraatBankService ziraatBankService;
    private IsBankService isBankService;

    private HashMap<String,BankService> hashMap;
    
    @Autowired
    public BankServiceModule(ZiraatBankService ziraatBankService,IsBankService isBankService) 
    {
        this.ziraatBankService= ziraatBankService;
        this.isBankService= isBankService;

        hashMap = new HashMap<>();

        hashMap.put("ZiraatBank", this.ziraatBankService);
        hashMap.put("IsBank", this.isBankService);
    }

    public BankService getBankService(String bankServiceName)
    {
        return hashMap.get(bankServiceName);
    }
}
