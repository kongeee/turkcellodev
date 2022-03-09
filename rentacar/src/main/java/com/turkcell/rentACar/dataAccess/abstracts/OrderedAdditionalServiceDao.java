package com.turkcell.rentACar.dataAccess.abstracts;

import com.turkcell.rentACar.entities.concretes.OrderedAdditionalService;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderedAdditionalServiceDao extends JpaRepository<OrderedAdditionalService,Integer> {
    
}
