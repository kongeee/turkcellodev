package com.turkcell.rentACar.business.requests.updates;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarRentalForCorporateCustomerRequest 
{   
    @Positive
    private int carRentalId;

    @NotNull
    @Positive
    private int carId;
    
    @NotNull
    @Positive
    private int corporateCustomerId;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate returnDate;

    @NotNull
    @NotBlank
    private int startCityId;

    @NotNull
    @NotBlank
    private int endCityId;

    @PositiveOrZero
    private double returnKilometer;
}

