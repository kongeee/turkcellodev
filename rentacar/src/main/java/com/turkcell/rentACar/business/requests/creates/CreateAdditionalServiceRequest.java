package com.turkcell.rentACar.business.requests.creates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAdditionalServiceRequest {
    
    @NotNull
    @Size(min = 2, max = 100)
    private String additionalServiceName;

    @NotNull
    @Min(0)
    private double dailyPrice;

    @NotNull
    @Size(min = 2)
    private String startCityName;
    
    @NotNull
    @Size(min = 2)
    private String endCityName;
}
