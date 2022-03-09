package com.turkcell.rentACar.business.requests.deletes;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteOrderedAdditionalServiceRequest {
    @NotNull
    private int orderedAdditionalServiceId;
}
