package com.turkcell.rentacar.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarByIdDto {
	private int carId;
	private double dailyPrice;
	private int modelYear;
	private String description;
	private String brandName;
	private String colorName;
}
