package com.turkcell.rentACar.business.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderedAdditionalServiceListDto 
{
    private int orderedAdditionalServiceId;
    private String additionalServiceName;

    @JsonProperty(value = "carRentalId")
    private int carRental_CarRentalId;
}
