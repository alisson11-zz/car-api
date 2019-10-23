package com.fictiusclean.car.api.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fictiusclean.car.api.Application;
import com.fictiusclean.car.api.dtos.CarDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
public class CarControllerTest {

	private static final String NAME = "Corcel";
	private static final String MODEL = "III";
	private static final String BRAND = "Ford";
	private static final String MANUFACTURING_DATE = "16/01/1996";
	private static final BigDecimal AVERAGE_CITY_CONSUMPTION = BigDecimal.valueOf( 10.05D );
	private static final BigDecimal AVERAGE_HIGHWAY_CONSUMPTION = BigDecimal.valueOf( 13.78D );

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void save() {
		ResponseEntity<CarDTO> response = restTemplate
				.postForEntity( "/v1/cars", buildCarDTO(), CarDTO.class );

		assertThat( response.getStatusCode(), equalTo( HttpStatus.CREATED ) );
		CarDTO resultDTO = response.getBody();
		assertThat( resultDTO.getName(), equalTo( NAME ) );
		assertThat( resultDTO.getModel(), equalTo( MODEL ) );
		assertThat( resultDTO.getBrand(), equalTo( BRAND ) );
		assertThat( resultDTO.getManufacturingDate(), equalTo( MANUFACTURING_DATE ) );
		assertThat( resultDTO.getAverageCityConsumption(), equalTo( AVERAGE_CITY_CONSUMPTION ) );
		assertThat( resultDTO.getAverageHighwayConsumption(), equalTo( AVERAGE_HIGHWAY_CONSUMPTION ) );
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
}
