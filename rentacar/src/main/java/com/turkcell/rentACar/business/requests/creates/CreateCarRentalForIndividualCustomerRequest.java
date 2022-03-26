package com.turkcell.rentACar.business.requests.creates;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCarRentalForIndividualCustomerRequest 
{
    @Positive
    private int carId;
    
    @Positive
    private int individualCustomerId;

    @NotNull
    private LocalDate startDate;
    
    @NotNull
    private LocalDate returnDate;
    
    @Positive
    private int startCityId;
    
    @Positive
    private int endCityId;

    @Positive
    private double startingKilometer;

    @Positive
    private double returnKilometer;
}
