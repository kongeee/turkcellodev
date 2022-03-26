package com.turkcell.rentACar.business.requests.creates;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerCardRequest
{
    
    private String cardOwnerName;
    private String cardNumber;
    private String cardCVC;
    private String cardDate;
    
    private int customerId;  
}

