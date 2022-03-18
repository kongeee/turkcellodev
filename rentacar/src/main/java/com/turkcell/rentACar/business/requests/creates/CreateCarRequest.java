package com.turkcell.rentACar.business.requests.creates;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CreateCarRequest {

    @NotBlank
    @NotNull
    @Min(50)
    @Max(500)
    private double carDailyPrice;

    @NotNull
    @NotBlank
    @Min(2000)
    @Max(2021)
    private int carModelYear;

    @Size(min=20,max=200)
    private String carDescription;

    @PositiveOrZero
    private double kilometerInformation;

    @NotBlank
    @NotNull
    @PositiveOrZero
    private int colorId;

    @NotBlank
    @NotNull
    @PositiveOrZero
    private int brandId;
}
