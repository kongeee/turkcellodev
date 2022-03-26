package com.turkcell.rentACar.dataAccess.abstracts;

import java.util.List;

import com.turkcell.rentACar.entities.concretes.CorporateCustomer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CorporateCustomerDao extends JpaRepository<CorporateCustomer,Integer> 
{
    boolean existsByTaxNumber(String taxNumber);

    @Query(value ="Select * From corparate_customers where corparate_customer_id<>:corporateCustomerId and tax_number=:taxNumber",nativeQuery = true)
	List<CorporateCustomer> getCorporateCustomerByTaxNumberAndNotEqualToId(@Param("corporateCustomerId") int corporateCustomerId, @Param("taxNumber") String taxNumber);
}