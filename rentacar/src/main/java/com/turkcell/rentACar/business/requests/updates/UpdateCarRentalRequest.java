package com.turkcell.rentACar.business.requests.updates;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarRentalRequest {
    @NotNull
    @NotBlank
    @Positive
    private int carRentalId;
	
	@NotNull
    @NotBlank
    @Positive
    private int carId;
    
    @NotNull
    @NotBlank
    private LocalDate startDate;
	
    @NotNull
    @NotBlank
    private LocalDate returnDate;
}
