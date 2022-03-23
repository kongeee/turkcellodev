package com.turkcell.rentACar.business.requests.creates;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarCrashInformationRequest 
{
    @NotNull
    @NotBlank
    private String crashDetail;

    @NotNull
    @NotBlank
    private int carId;
}