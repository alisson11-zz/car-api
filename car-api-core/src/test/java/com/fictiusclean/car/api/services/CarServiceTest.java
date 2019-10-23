package com.fictiusclean.car.api.services;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.fictiusclean.car.api.dtos.CarDTO;
import com.fictiusclean.car.api.dtos.CarDetailsRequestDTO;
import com.fictiusclean.car.api.entities.Car;
import com.fictiusclean.car.api.mappers.CarMapper;
import com.fictiusclean.car.api.repositories.CarRepository;

@RunWith(MockitoJUnitRunner.class)
public class CarServiceTest {

	@Mock
	private CarRepository carRepository;
	@Mock
	private CarMapper mapper;
	@Mock
	private ValidationService validationService;
	@Mock
	private ExpensesCalculatorService expensesCalculatorService;
	@InjectMocks
	private CarService carService;

	@Test
	public void whenSave() {
		CarDTO carDTO = new CarDTO();
		Car carEntity = new Car();
		when( mapper.toEntity( carDTO ) ).thenReturn( carEntity );

		carService.save( carDTO );

		verifyZeroInteractions( expensesCalculatorService );
		verify( validationService ).validate( carEntity );
		verify( mapper ).toEntity( carDTO );
		verify( carRepository ).save( carEntity );
	}

	@Test
	public void whenRetrieveConsumptionDetails() {
		CarDetailsRequestDTO detailsRequest = new CarDetailsRequestDTO();
		List<Car> carList = Collections.singletonList( new Car() );
		when( carRepository.findAll() ).thenReturn( carList );

		carService.retrieveTotalExpenses( detailsRequest );

		verifyZeroInteractions( mapper );
		verify( validationService ).validate( detailsRequest );
		verify( carRepository ).findAll();
		verify( expensesCalculatorService ).retrieveDetails( carList, detailsRequest );
	}
}
