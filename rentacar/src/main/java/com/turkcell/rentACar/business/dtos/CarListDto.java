package com.turkcell.rentACar.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class CarListDto {
	private int carId;
    private double carDailyPrice;
    private int carModelYear;
    private String carDescription;
    private double kilometerInformation;
    private String brandName;
    private String colorName;
}
