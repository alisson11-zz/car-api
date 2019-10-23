package com.fictiusclean.car.api.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.fictiusclean.car.api.dtos.CarDTO;
import com.fictiusclean.car.api.dtos.CarDetailsRequestDTO;
import com.fictiusclean.car.api.dtos.CarDetailsResponseDTO;
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

	@Test
	public void whenRetrieveConsumptionDetails() {
		CarDetailsRequestDTO detailsRequest = buildRequest();
		when( carRepository.findAll() ).thenReturn( Collections.singletonList( buildCar() ) );

		List<CarDetailsResponseDTO> resultList = carService.retrieveTotalExpenses( detailsRequest );

		verifyZeroInteractions( mapper );
		verify( validationService ).validate( detailsRequest );
		verify( carRepository ).findAll();
		assertThat( resultList, hasSize( 1 ) );
		CarDetailsResponseDTO responseDTO = resultList.get( 0 );
		assertThat( responseDTO.getName(), equalTo( NAME ) );
		assertThat( responseDTO.getBrand(), equalTo( BRAND ) );
		assertThat( responseDTO.getModel(), equalTo( MODEL ) );
		assertThat( responseDTO.getYear(), equalTo( 1996 ) );
		assertThat( responseDTO.getSpentFuelTotalQuantity(), comparesEqualTo( BigDecimal.valueOf( 24.464D ) ) );
		assertThat( responseDTO.getSpentFuelTotalValue(), comparesEqualTo( BigDecimal.valueOf( 97.122D ) ) );
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

	private CarDetailsRequestDTO buildRequest() {
		return CarDetailsRequestDTO.builder()
				.fuelPrice( BigDecimal.valueOf( 3.97D ) )
				.cityRouteKilometers( BigDecimal.valueOf( 100D ) )
				.highwayRouteKilometers( BigDecimal.valueOf( 200D ) )
				.build();
	}
}
