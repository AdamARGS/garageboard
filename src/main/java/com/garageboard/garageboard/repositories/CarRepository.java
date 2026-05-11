package com.garageboard.garageboard.repositories;

import com.garageboard.garageboard.entities.Car;
import com.garageboard.garageboard.entities.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByUser(User user);
}