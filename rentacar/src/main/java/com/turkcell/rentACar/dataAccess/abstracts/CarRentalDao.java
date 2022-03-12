package com.turkcell.rentACar.dataAccess.abstracts;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACar.entities.concretes.CarRental;

@Repository
public interface CarRentalDao extends JpaRepository<CarRental, Integer> {
	List<CarRental> getAllByCar_CarId(Integer id);
	@Query(value ="Select * From car_rental where car_id=:carId Order By car_rental_id Desc Limit 1", nativeQuery = true)
	CarRental getByRecentlyAddedVehicleId(@Param("carId") int carId);
	
	@Query(value ="From CarRental where (startDate>=:startDate and startDate<=:returnDate) or (returnDate<=:startDate and returnDate>=:returnDate) and car_id=:carId")
	List<CarRental> getRentalInformationOfTheVehicleOnTheSpecifiedDate(@Param("startDate") LocalDate startDate,@Param("returnDate") LocalDate returnDate,
			@Param("carId") int carId);
}
