package com.fictiusclean.car.api.services;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.fictiusclean.car.api.dtos.CarDTO;
import com.fictiusclean.car.api.entities.Car;
import com.fictiusclean.car.api.mappers.CarMapper;
import com.fictiusclean.car.api.repositories.CarRepository;

@RunWith(MockitoJUnitRunner.class)
public class CarServiceTest {

	private static final String NAME = "Corcel";
	private static final String MODEL = "III";
	private static final String BRAND = "Ford";
	private static final String MANUFACTURING_DATE = "16/01/1996";
	private static final Date MANUFACTURING_DATE_AS_DATE = Date
			.from( LocalDate.of( 1996, Month.JANUARY, 16 ).atStartOfDay( ZoneId.systemDefault() ).toInstant() );
	private static final BigDecimal AVERAGE_CITY_CONSUMPTION = BigDecimal.valueOf( 10.05D );
	private static final BigDecimal AVERAGE_HIGHWAY_CONSUMPTION = BigDecimal.valueOf( 13.78D );

	@Mock
	private CarRepository carRepository;
	@Mock
	private CarMapper mapper;
	@Mock
	private ValidationService validationService;
	@InjectMocks
	private CarService carService;

	@Test
	public void whenSave() {
		CarDTO carDTO = buildCarDTO();
		Car carEntity = buildCar();
		when( mapper.toEntity( carDTO ) ).thenReturn( carEntity );

		carService.save( carDTO );

		verify( validationService ).validate( carEntity );
		verify( mapper ).toEntity( carDTO );
		verify( carRepository ).save( carEntity );
	}

	private CarDTO buildCarDTO() {
		return CarDTO.builder()
				.model( MODEL )
				.name( NAME )
				.brand( BRAND )
				.manufacturingDate( MANUFACTURING_DATE )
				.averageCityConsumption( AVERAGE_CITY_CONSUMPTION )
				.averageHighwayConsumption( AVERAGE_HIGHWAY_CONSUMPTION )
				.build();
	}

	private Car buildCar() {
		return Car.builder()
				.model( MODEL )
				.name( NAME )
				.brand( BRAND )
				.manufacturingDate( MANUFACTURING_DATE_AS_DATE )
				.averageCityConsumption( AVERAGE_CITY_CONSUMPTION )
				.averageHighwayConsumption( AVERAGE_HIGHWAY_CONSUMPTION )
				.build();
	}
}
