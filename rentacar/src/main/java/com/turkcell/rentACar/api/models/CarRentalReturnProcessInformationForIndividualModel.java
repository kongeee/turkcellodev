package com.turkcell.rentACar.api.models;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarRentalReturnProcessInformationForIndividualModel 
{
    @Positive
    private int carRentalId;

    @Positive
    private int individualCustomerId;

    @NotNull
    private LocalDate returnDate;

    @Positive
    private double returnKilometer;
}