package com.turkcell.rentACar.business.requests.creates;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreeateCarRentalForIndividualCustomerRequest 
{    
    @NotNull
    @Positive
    private int carId;
    
    @Positive
    private int individualCustomerId;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate returnDate;

    @NotNull
    private String startCity;

    @NotNull
    private String endCity;
}