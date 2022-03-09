package com.turkcell.rentACar.business.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderedAdditionalServiceDto {
    private int orderedAdditionalServiceId;
    private String additionalServiceName;
    private int carId;
}
