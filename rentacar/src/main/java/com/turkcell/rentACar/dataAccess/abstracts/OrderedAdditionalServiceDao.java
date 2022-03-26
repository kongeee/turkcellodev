package com.turkcell.rentACar.dataAccess.abstracts;

import java.util.List;

import com.turkcell.rentACar.entities.concretes.OrderedAdditionalService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderedAdditionalServiceDao extends JpaRepository<OrderedAdditionalService,Integer> 
{
	@Query(value = "Delete From ordered_additional_service Where car_rental_id=:carRentalId",nativeQuery = true)
	void deleteAllByCarRental_CarRentalId(@Param("carRentalId") int carRentalId);

	@Query(value = "Select * From ordered_additional_service Where car_rental_id=:carRentalId",nativeQuery = true)
	List<OrderedAdditionalService> getAllByOrderedAdditionalService_CarRentalId(Integer carRentalId);
}
