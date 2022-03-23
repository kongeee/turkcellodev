package com.turkcell.rentACar.business.requests.updates;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarCrashInformationRequest 
{
    @Positive
    private int carCrashInformationId;

    @NotNull
    @NotBlank
    private String crashDetail;

    @Positive
    private int carId;
}