package com.turkcell.rentACar.dataAccess.abstracts;


import java.util.List;

import com.turkcell.rentACar.entities.concretes.CustomerCard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerCardDao extends JpaRepository<CustomerCard,Integer>  {
    boolean existsByCardNumber(String cardNumber);

    @Query(value ="Select * From customer_cards where customer_id<>:customerId and card_number=:cardNumber",nativeQuery = true)
	List<CustomerCard> getCustomerCardByCardNumberAndNotEqualToId(@Param("customerId") int customerId, @Param("cardNumber") String cardNumber);
}
