package com.turkcell.rentACar.dataAccess.abstracts;

import com.turkcell.rentACar.entities.concretes.CorporateCustomer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CorporateCustomerDao extends JpaRepository<CorporateCustomer,Integer> {
    boolean existsByTaxNumber(String taxNumber);
}
