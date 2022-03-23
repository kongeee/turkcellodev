package com.turkcell.rentACar.core.utilities.posService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PosInformation 
{
    private String cardOwnerName;
    private String cardNumber;
    private String cardCVC;
    private String cardDate;  
}
