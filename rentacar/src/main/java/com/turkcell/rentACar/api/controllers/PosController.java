package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.PosService;
import com.turkcell.rentACar.business.dependencyResolvers.BankServiceModule;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.posService.PosInformation;
import com.turkcell.rentACar.core.utilities.results.Result;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/PosServices")
public class PosController 
{
    private PosService posService;
    private BankServiceModule bankServiceModule;

    public PosController(PosService posService,BankServiceModule bankServiceModule) 
    {
        this.posService= posService;
        this.bankServiceModule = bankServiceModule;
    }

    @PostMapping("/pay")
    public Result pay(@RequestBody PosInformation posInformation, @RequestParam double price) throws BusinessException
    {
        return this.posService.pay(posInformation, price, bankServiceModule.getBankService("ZiraatBank"));
    }
}