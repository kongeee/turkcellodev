package com.turkcell.rentACar.business.requests.creates;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCarRentalForIndividualCustomerRequest {
    
    @NotNull
    @Positive
    private int carId;
    
    private int individualCustomerId;
    private List<Integer> additionalServiceIds;
    private LocalDate startDate;
    private LocalDate returnDate;
    private String startCity;
    private String endCity;
}
