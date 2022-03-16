package com.turkcell.rentACar.dataAccess.abstracts;

import com.turkcell.rentACar.entities.concretes.OrderedAdditionalService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OrderedAdditionalServiceDao extends JpaRepository<OrderedAdditionalService,Integer> 
{
	@Modifying
	@Query(value = "Delete From ordered_additional_service Where car_rental_id=:carRentalId",nativeQuery = true)
	void deleteAllByCarRental_CarRentalId(@Param("carRentalId") int carRentalId);
}
