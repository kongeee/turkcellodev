package com.turkcell.rentACar.dataAccess.abstracts;

import com.turkcell.rentACar.core.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User,Integer>{
    
}
