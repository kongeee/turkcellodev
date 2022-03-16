package com.turkcell.rentACar.business.dtos;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarRentalListDto {
    private int carRentalId;
    private LocalDate startDate;
    private LocalDate returnDate;
    private String startCityName;
    private String endCityName;
    
    @JsonProperty("carId")
    private int car_CarId;
}
