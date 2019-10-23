package com.fictiusclean.car.api.controllers;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fictiusclean.car.api.dtos.CarDTO;
import com.fictiusclean.car.api.services.CarService;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/cars")
public class CarController {

	private CarService carService;

	@PostMapping
	public ResponseEntity save(@RequestBody CarDTO dto) {
		return new ResponseEntity( carService.save( dto ), HttpStatus.CREATED );
	}
}
