package com.turkcell.rentACar.business.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerCardDto 
{
    private int customerCardId;
    private String cardOwnerName;
    private String cardNumber;
    private String cardCVC;
    private String cardDate;

    @JsonProperty(value = "customerId")
    private int customer_CustomerId;

}
