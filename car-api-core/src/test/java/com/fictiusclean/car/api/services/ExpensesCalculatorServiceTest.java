package com.fictiusclean.car.api.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.hasSize;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.fictiusclean.car.api.dtos.CarDetailsRequestDTO;
import com.fictiusclean.car.api.dtos.CarDetailsResponseDTO;
import com.fictiusclean.car.api.entities.Car;

public class ExpensesCalculatorServiceTest {

	private static final String NAME = "Corcel";
	private static final String MODEL = "III";
	private static final String BRAND = "Ford";
	private static final Date MANUFACTURING_DATE_AS_DATE = Date
			.from( LocalDate.of( 1996, Month.JANUARY, 16 ).atStartOfDay( ZoneId.systemDefault() ).toInstant() );
	private static final BigDecimal AVERAGE_CITY_CONSUMPTION = BigDecimal.valueOf( 10.05D );
	private static final BigDecimal AVERAGE_HIGHWAY_CONSUMPTION = BigDecimal.valueOf( 13.78D );

	private ExpensesCalculatorService service = new ExpensesCalculatorService();

	@Test
	public void calculate() {
		List<Car> carList = Collections.singletonList( buildCar() );
		List<CarDetailsResponseDTO> resultList = service.retrieveDetails( carList, buildRequest() );

		assertThat( resultList, hasSize( 1 ) );
		CarDetailsResponseDTO responseDTO = resultList.get( 0 );
		assertThat( responseDTO.getName(), equalTo( NAME ) );
		assertThat( responseDTO.getBrand(), equalTo( BRAND ) );
		assertThat( responseDTO.getModel(), equalTo( MODEL ) );
		assertThat( responseDTO.getYear(), equalTo( 1996 ) );
		assertThat( responseDTO.getTotalFuelLitersSpentQuantity(), comparesEqualTo( BigDecimal.valueOf( 24.464D ) ) );
		assertThat( responseDTO.getTotalFuelExpenseValue(), comparesEqualTo( BigDecimal.valueOf( 97.12D ) ) );
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
