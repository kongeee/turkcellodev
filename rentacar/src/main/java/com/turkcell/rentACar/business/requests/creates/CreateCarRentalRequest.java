package com.turkcell.rentACar.business.requests.creates;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRentalRequest {
    @NotNull
    @NotBlank
    @Positive
    private int carId;
    
    @NotNull
    @NotBlank
    private LocalDate startDate;

    @NotNull
    @NotBlank
    private LocalDate returnDate;

    @NotNull
    @NotBlank
    private String startCityName;

    @NotNull
    @NotBlank
    private String endCityName;
    
    @NotNull
    private List<Integer> addtionalServicesId;
}
