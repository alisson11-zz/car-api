package com.fictiusclean.car.api.mappers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;

import org.junit.Test;

import com.fictiusclean.car.api.dtos.CarDTO;
import com.fictiusclean.car.api.entities.Car;

public class CarMapperTest {

	private static final String NAME = "Corcel";
	private static final String MODEL = "III";
	private static final String BRAND = "Ford";
	private static final String MANUFACTURING_DATE = "16/01/1996";
	private static final Date MANUFACTURING_DATE_AS_DATE = Date
			.from( LocalDate.of( 1996, Month.JANUARY, 16 ).atStartOfDay( ZoneId.systemDefault() ).toInstant() );
	private static final BigDecimal AVERAGE_CITY_CONSUMPTION = BigDecimal.valueOf( 10.05D );
	private static final BigDecimal AVERAGE_HIGHWAY_CONSUMPTION = BigDecimal.valueOf( 13.78D );

	private CarMapper mapper = new CarMapperImpl();

	@Test
	public void toEntityWhenNull() {
		assertThat( mapper.toEntity( null ), nullValue() );
	}

	@Test
	public void toDTOWhenNull() {
		assertThat( mapper.toDTO( null ), nullValue() );
	}

	@Test
	public void toEntity() {
		Car resultCar = mapper.toEntity( buildCarDTO() );

		assertThat( resultCar, notNullValue() );
		assertThat( resultCar.getName(), equalTo( NAME ) );
		assertThat( resultCar.getModel(), equalTo( MODEL ) );
		assertThat( resultCar.getBrand(), equalTo( BRAND ) );
		assertThat( resultCar.getManufacturingDate(), equalTo( MANUFACTURING_DATE_AS_DATE ) );
		assertThat( resultCar.getAverageCityConsumption(), equalTo( AVERAGE_CITY_CONSUMPTION ) );
		assertThat( resultCar.getAverageHighwayConsumption(), equalTo( AVERAGE_HIGHWAY_CONSUMPTION ) );
	}

	@Test
	public void toDTO() {
		CarDTO resultCar = mapper.toDTO( buildCar() );

		assertThat( resultCar, notNullValue() );
		assertThat( resultCar.getName(), equalTo( NAME ) );
		assertThat( resultCar.getModel(), equalTo( MODEL ) );
		assertThat( resultCar.getBrand(), equalTo( BRAND ) );
		assertThat( resultCar.getManufacturingDate(), equalTo( MANUFACTURING_DATE ) );
		assertThat( resultCar.getAverageCityConsumption(), equalTo( AVERAGE_CITY_CONSUMPTION ) );
		assertThat( resultCar.getAverageHighwayConsumption(), equalTo( AVERAGE_HIGHWAY_CONSUMPTION ) );
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
