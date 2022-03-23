package com.turkcell.rentACar.business.requests.updates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderedAdditionalServiceRequest 
{
    @NotNull
    private int orderedAdditionalServiceId;
    
    @NotNull
    private int carRentalId;

    @NotNull
    private int additionalServiceId;    
}