package com.turkcell.rentACar.business.dtos;

import com.turkcell.rentACar.entities.concretes.CarMaintenance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {
    private double carDailyPrice;
    private int carModelYear;
    private String carDescription;
    private String brandName;
    private String colorName;
}
