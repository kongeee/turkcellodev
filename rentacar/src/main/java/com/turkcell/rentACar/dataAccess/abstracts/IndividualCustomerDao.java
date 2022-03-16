package com.turkcell.rentACar.dataAccess.abstracts;

import java.util.List;

import com.turkcell.rentACar.entities.concretes.IndividualCustomer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IndividualCustomerDao extends JpaRepository<IndividualCustomer,Integer>
{
    boolean existsByNationalIdentity(String nationalIdentity);

    @Query(value ="Select * From individual_customers where individual_customer_id<>:individualCustomerId and national_identity=:nationalIdentity",nativeQuery = true)
	List<IndividualCustomer> getIndividualCustomerByNationalIdentityAndNotEqualToId(@Param("individualCustomerId") int individualCustomerId, @Param("nationalIdentity") String nationalIdentity);
}