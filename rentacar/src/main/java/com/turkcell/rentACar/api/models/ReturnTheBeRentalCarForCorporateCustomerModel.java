package com.turkcell.rentACar.api.models;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.turkcell.rentACar.core.utilities.bankServices.BankInformation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnTheBeRentalCarForCorporateCustomerModel 
{
    @Positive
    private int carRentalId;

    @Positive
    private int corporateCustomerId;

    @NotNull
    private LocalDate returnDate;
    
    @Positive
    private double returnKilometer;

    @NotNull
    private BankInformation bankInformation;
}
