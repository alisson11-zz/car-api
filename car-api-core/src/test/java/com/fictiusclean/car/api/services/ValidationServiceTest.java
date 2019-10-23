package com.fictiusclean.car.api.services;

import org.junit.Test;

import com.fictiusclean.car.api.entities.Car;
import com.fictiusclean.car.api.exceptions.ValidationException;

public class ValidationServiceTest {

	private ValidationService validationService = new ValidationService();

	@Test(expected = ValidationException.class)
	public void validateCar() {
		validationService.validate( new Car() );
	}
}
