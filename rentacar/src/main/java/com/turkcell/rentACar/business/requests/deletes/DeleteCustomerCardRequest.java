package com.turkcell.rentACar.business.requests.deletes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCustomerCardRequest {

    private int customerCardId;
    
}
