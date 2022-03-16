package com.turkcell.rentACar.dataAccess.abstracts;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACar.entities.concretes.CarRental;

@Repository
public interface CarRentalDao extends JpaRepository<CarRental, Integer> 
{
	
	boolean existsByCarRentalId(int carRenatalId);

	List<CarRental> getAllByCar_CarId(Integer id);
	
	@Query(value ="From CarRental where (startDate>=:startDate and startDate<=:returnDate) or (returnDate<=:startDate and returnDate>=:returnDate) and car_id=:carId")
	List<CarRental> getRentalInformationOfTheCarOnTheSpecifiedDate(@Param("startDate") LocalDate startDate,@Param("returnDate") LocalDate returnDate,
			@Param("carId") int carId);

	@Query(value ="Select * From car_rental where ((start_date>=:startDate and start_date<=:returnDate) or (return_date<=:startDate and return_date>=:returnDate)) and car_rental_id<>:carRentalId  and car_id=:carId",nativeQuery = true)
	List<CarRental> getRentalInformationOfTheCarOnTheSpecifiedDate(@Param("startDate") LocalDate startDate,@Param("returnDate") LocalDate returnDate,
			@Param("carId") int carId, @Param("carRentalId") int carRentalId);
}
