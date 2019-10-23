package com.fictiusclean.car.api.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fictiusclean.car.api.entities.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, UUID> {

}
