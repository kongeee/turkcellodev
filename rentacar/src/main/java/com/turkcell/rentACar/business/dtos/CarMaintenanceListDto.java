package com.turkcell.rentACar.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarMaintenanceListDto {

    private int maintenanceId;

    private String maintenanceDescription;

    private LocalDate returnDate;

    private int carId;

}
