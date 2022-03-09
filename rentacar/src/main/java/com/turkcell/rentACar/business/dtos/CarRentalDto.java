package com.turkcell.rentACar.business.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarRentalDto {
    private int carRentalId;
    private LocalDate startDate;
    private LocalDate returnDate;
    private String startCityName;
    private String endCityName;
    private int carId;
}
