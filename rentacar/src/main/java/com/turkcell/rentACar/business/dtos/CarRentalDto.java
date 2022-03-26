package com.turkcell.rentACar.business.dtos;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarRentalDto 
{
    private int carRentalId;
    private LocalDate startDate;
    private LocalDate returnDate;

    @JsonProperty(value = "startCityId")
    private int startCity_CityId;

    @JsonProperty(value = "endCityId")
    private int endCity_cityId;

    @JsonProperty(value = "carId")
    private int car_CarId;
}