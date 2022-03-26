package com.turkcell.rentACar.api.models;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.turkcell.rentACar.core.utilities.bankServices.BankInformation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor 
public class CarRentalTransactionInformationForCorporateCustomerModel 
{
    @Positive
    private int carId;
    
    @Positive
    private int corporateCustomerId;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate returnDate;

    @Positive
    private int startCityId;

    @Positive
    private int endCityId;

    @NotNull
    private List<Integer> orderedAdditionalServiceIds;
    
    @NotNull
    private BankInformation bankInformation;
}
