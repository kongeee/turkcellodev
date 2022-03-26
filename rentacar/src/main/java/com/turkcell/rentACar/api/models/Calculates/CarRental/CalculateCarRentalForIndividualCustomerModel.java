package com.turkcell.rentACar.api.models.Calculates.CarRental;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor 
public class CalculateCarRentalForIndividualCustomerModel 
{
    @NotNull
    @Positive
    private int carId;

    @NotNull
    private LocalDate startDate;
    
    @NotNull
    private LocalDate returnDate;
    
    @Positive
    private int startCityId;
    
    @Positive
    private int endCityId;
}
