package com.fictiusclean.car.api.services;

import java.util.List;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fictiusclean.car.api.dtos.CarDTO;
import com.fictiusclean.car.api.dtos.CarDetailsRequestDTO;
import com.fictiusclean.car.api.dtos.CarDetailsResponseDTO;
import com.fictiusclean.car.api.entities.Car;
import com.fictiusclean.car.api.mappers.CarMapper;
import com.fictiusclean.car.api.repositories.CarRepository;

@Service
@AllArgsConstructor
public class CarService {

	private CarRepository carRepository;
	private CarMapper mapper;
	private ValidationService validationService;
	private ExpensesCalculatorService expensesCalculatorService;

	@Transactional
	public CarDTO save(CarDTO carDTO) {
		Car carEntity = mapper.toEntity( carDTO );
		validationService.validate( carEntity );
		return mapper.toDTO( carRepository.save( carEntity ) );
	}

	public List<CarDetailsResponseDTO> retrieveTotalExpenses(CarDetailsRequestDTO request) {
		validationService.validate( request );
		return expensesCalculatorService.retrieveDetails( carRepository.findAll(), request );
	}
}
