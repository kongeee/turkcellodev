package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.api.models.CarRentalReturnProcessInformationForCorporateModel;
import com.turkcell.rentACar.api.models.CarRentalReturnProcessInformationForIndividualModel;
import com.turkcell.rentACar.api.models.CarRentalTransactionInformationForCorporateCustomerModel;
import com.turkcell.rentACar.api.models.CarRentalTransactionInformationForIndividualCustomerModel;
import com.turkcell.rentACar.api.models.ReturnTheBeRentalCarForCorporateCustomerModel;
import com.turkcell.rentACar.api.models.ReturnTheBeRentalCarForIndividualCustomerModel;
import com.turkcell.rentACar.business.abstracts.CarRentalTransactionsService;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/CarRentalTransactions")
public class CarRentalTransactionsController 
{
    private CarRentalTransactionsService carRentalTransactionsService;

    @Autowired
    public CarRentalTransactionsController(CarRentalTransactionsService carRentalTransactionsService) 
    {
        this.carRentalTransactionsService = carRentalTransactionsService;
    }

    @PostMapping("/makeACarRentalForCorporateCustomer")
    public Result MakeACarRentalForCorporateCustomer(@RequestBody CarRentalTransactionInformationForCorporateCustomerModel carRentalTransactionInformationForCorporateCustomerModel) throws BusinessException
    {
        return carRentalTransactionsService.MakeACarRentalForCorporateCustomer(carRentalTransactionInformationForCorporateCustomerModel);
    }


    @PostMapping("/makeACarRentalForIndividualCustomer")
    public Result MakeACarRentalForIndividualCustomer(@RequestBody CarRentalTransactionInformationForIndividualCustomerModel carRentalTransactionInformationForIndividualCustomerModel) throws BusinessException
    {
        return carRentalTransactionsService.MakeACarRentalForIndivualCustomer(carRentalTransactionInformationForIndividualCustomerModel);
    }

    @PostMapping("/returnTheRentalCarForCorporateCustomer")
    Result ReturnTheRentalCarForCorporateCustomer(@RequestBody CarRentalReturnProcessInformationForCorporateModel carRentalReturnProcessInformationForCorporateModel) throws BusinessException
    {
        return carRentalTransactionsService.ReturnTheRentalCarForCorporateCustomer(carRentalReturnProcessInformationForCorporateModel);
    }

    @PostMapping("/returnTheRentalCarForIndivualCustomer")
    Result ReturnTheRentalCarForIndivualCustomer(@RequestBody CarRentalReturnProcessInformationForIndividualModel carRentalReturnProcessInformationForIndividualModel) throws BusinessException
    {
        return carRentalTransactionsService.ReturnTheRentalCarForIndivualCustomer(carRentalReturnProcessInformationForIndividualModel);
    }
    
    @PostMapping("/returnTheBeTardyRentalCarForCorporateCustomer")
    Result ReturnTheBeTardyRentalCarForCorporateCustomer(@RequestBody ReturnTheBeRentalCarForCorporateCustomerModel returnTheBeRentalCarForCorporateCustomerModel) throws BusinessException
    {
        return carRentalTransactionsService.ReturnTheBeTardyRentalCarForCorporateCustomer(returnTheBeRentalCarForCorporateCustomerModel);
    }

    @PostMapping("/returnTheBeTardyRentalCarForIndivualCustomer")
    Result ReturnTheBeTardyRentalCarForIndivualCustomer(@RequestBody ReturnTheBeRentalCarForIndividualCustomerModel returnTheBeRentalCarForIndividualCustomerModel) throws BusinessException
    {
        return carRentalTransactionsService.ReturnTheBeTardyRentalCarForIndivualCustomer(returnTheBeRentalCarForIndividualCustomerModel);
    }
}
