package com.turkcell.rentACar.business.requests.deletes;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCarRentalRequest {

    @NotNull
    @NotBlank
    @Positive
    private int carRentalId;
}
