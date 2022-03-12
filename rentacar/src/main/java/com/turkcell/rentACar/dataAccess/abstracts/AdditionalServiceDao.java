package com.turkcell.rentACar.dataAccess.abstracts;

import com.turkcell.rentACar.entities.concretes.AdditionalService;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdditionalServiceDao extends JpaRepository<AdditionalService,Integer> 
{
	@Query(value ="Select * From additional_service where additional_service_id in (:additionalServicesIds)", nativeQuery = true)
    List<AdditionalService>  getAdditionalServicesByIds(@Param("additionalServicesIds") List<Integer> additionalServicesIds);
    
	boolean existsByAdditionalServiceName(String additionalServiceName);
}
