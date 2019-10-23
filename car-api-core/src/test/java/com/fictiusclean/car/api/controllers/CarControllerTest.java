package com.fictiusclean.car.api.controllers;

import static java.math.BigDecimal.valueOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.hasSize;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.fictiusclean.car.api.Application;
import com.fictiusclean.car.api.dtos.CarDTO;
import com.fictiusclean.car.api.dtos.CarDetailsRequestDTO;
import com.fictiusclean.car.api.dtos.CarDetailsResponseDTO;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
public class CarControllerTest {

	private static final String FIRST_NAME = "Corcel";
	private static final String SECOND_NAME = "Belina";
	private static final String MODEL = "III";
	private static final String BRAND = "Ford";
	private static final String FIRST_DATE = "16/01/1996";
	private static final String SECOND_DATE = "11/05/1994";
	private static final int FIRST_YEAR = 1996;
	private static final int SECOND_YEAR = 1994;
	private static final BigDecimal FIRST_CITY_CONSUMPTION = valueOf( 10.05D );
	private static final BigDecimal FIRST_HIGHWAY_CONSUMPTION = valueOf( 13.78D );
	private static final BigDecimal SECOND_CITY_CONSUMPTION = valueOf( 8.11D );
	private static final BigDecimal SECOND_HIGHWAY_CONSUMPTION = valueOf( 9.95D );

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void save() {
		ResponseEntity<CarDTO> response = doPost(
				buildCarDTO( FIRST_NAME, FIRST_DATE, FIRST_CITY_CONSUMPTION, FIRST_HIGHWAY_CONSUMPTION ) );

		assertThat( response.getStatusCode(), equalTo( HttpStatus.CREATED ) );
		CarDTO resultDTO = response.getBody();
		assertThat( resultDTO.getName(), equalTo( FIRST_NAME ) );
		assertThat( resultDTO.getModel(), equalTo( MODEL ) );
		assertThat( resultDTO.getBrand(), equalTo( BRAND ) );
		assertThat( resultDTO.getManufacturingDate(), equalTo( FIRST_DATE ) );
		assertThat( resultDTO.getAverageCityConsumption(), equalTo( FIRST_CITY_CONSUMPTION ) );
		assertThat( resultDTO.getAverageHighwayConsumption(), equalTo( FIRST_HIGHWAY_CONSUMPTION ) );
	}

	@Test
	public void retrieveConsumptionDetails() {
		doPost( buildCarDTO( FIRST_NAME, FIRST_DATE, FIRST_CITY_CONSUMPTION, FIRST_HIGHWAY_CONSUMPTION ) );
		doPost( buildCarDTO( SECOND_NAME, SECOND_DATE, SECOND_CITY_CONSUMPTION, SECOND_HIGHWAY_CONSUMPTION ) );

		ResponseEntity<List<CarDetailsResponseDTO>> detailsResult = doRetrieveDetails();

		assertThat( detailsResult.getStatusCode(), equalTo( HttpStatus.OK ) );
		List<CarDetailsResponseDTO> resultListDTO = detailsResult.getBody();
		assertThat( resultListDTO, hasSize( 2 ) );
		CarDetailsResponseDTO responseDTO = resultListDTO.get( 0 );
		assertValues( resultListDTO.get( 0 ), FIRST_NAME, FIRST_YEAR, valueOf( 24.464D ), valueOf( 97.122D ) );
		assertValues( resultListDTO.get( 1 ), SECOND_NAME, SECOND_YEAR, valueOf( 32.431D ), valueOf( 128.751D ) );
	}

	private void assertValues(CarDetailsResponseDTO responseDTO, String name, int year, BigDecimal cityConsumption,
			BigDecimal highwayConsumption) {
		assertThat( responseDTO.getName(), equalTo( name ) );
		assertThat( responseDTO.getModel(), equalTo( MODEL ) );
		assertThat( responseDTO.getBrand(), equalTo( BRAND ) );
		assertThat( responseDTO.getYear(), equalTo( year ) );
		assertThat( responseDTO.getSpentFuelTotalQuantity(), comparesEqualTo( cityConsumption ) );
		assertThat( responseDTO.getSpentFuelTotalValue(), equalTo( highwayConsumption ) );
	}

	private ResponseEntity<CarDTO> doPost(CarDTO carDTO) {
		return restTemplate.postForEntity( "/v1/cars", carDTO, CarDTO.class );
	}

	private ResponseEntity<List<CarDetailsResponseDTO>> doRetrieveDetails() {
		return restTemplate.exchange( "/v1/cars/consumption/details",
				HttpMethod.POST,
				new HttpEntity( buildRequest() ),
				new ParameterizedTypeReference<List<CarDetailsResponseDTO>>() {
				} );
	}

	private CarDTO buildCarDTO(String name, String manufacturingDate, BigDecimal cityConsumtpion,
			BigDecimal highwayConsumption) {
		return CarDTO.builder()
				.model( MODEL )
				.name( name )
				.brand( BRAND )
				.manufacturingDate( manufacturingDate )
				.averageCityConsumption( cityConsumtpion )
				.averageHighwayConsumption( highwayConsumption )
				.build();
	}

	private CarDetailsRequestDTO buildRequest() {
		return CarDetailsRequestDTO.builder()
				.fuelPrice( valueOf( 3.97D ) )
				.cityRouteKilometers( valueOf( 100D ) )
				.highwayRouteKilometers( valueOf( 200D ) )
				.build();
	}
}
