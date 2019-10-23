package com.fictiusclean.car.api.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
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

	@Transactional
	public CarDTO save(CarDTO carDTO) {
		Car carEntity = mapper.toEntity( carDTO );
		validationService.validate( carEntity );
		return mapper.toDTO( carRepository.save( carEntity ) );
	}

	public List<CarDetailsResponseDTO> retrieveTotalExpenses(CarDetailsRequestDTO request) {
		validationService.validate( request );
		List<Car> carList = carRepository.findAll();
		List<CarDetailsResponseDTO> responseDTOList = new ArrayList();
		carList.forEach( car -> calculate( car, request, responseDTOList ) );
		responseDTOList.sort( Comparator.comparing( CarDetailsResponseDTO::getSpentFuelTotalValue ) );
		return responseDTOList;
	}

	private void calculate(Car car, CarDetailsRequestDTO request, List<CarDetailsResponseDTO> responseDTOList) {
		BigDecimal cityLiters = calculate( request.getCityRouteKilometers(), car.getAverageCityConsumption() );
		BigDecimal highwayLiters = calculate( request.getHighwayRouteKilometers(), car.getAverageHighwayConsumption() );
		addToList( car, request, responseDTOList, sum( cityLiters, highwayLiters ) );
	}

	private void addToList(Car car, CarDetailsRequestDTO request, List<CarDetailsResponseDTO> responseDTOList,
			BigDecimal totalSpentFuel) {
		responseDTOList.add( CarDetailsResponseDTO.builder()
				.name( car.getName() )
				.brand( car.getBrand() )
				.model( car.getModel() )
				.year( LocalDate.ofInstant( car.getManufacturingDate().toInstant(), ZoneId.systemDefault() ).getYear() )
				.spentFuelTotalQuantity( totalSpentFuel )
				.spentFuelTotalValue( multiply( totalSpentFuel, request.getFuelPrice() ) )
				.build() );
	}

	private BigDecimal calculate(BigDecimal totalRouteKilometers, BigDecimal totalConsumption) {
		return totalRouteKilometers.divide( totalConsumption, 3, RoundingMode.HALF_EVEN );
	}

	private BigDecimal sum(BigDecimal firstValue, BigDecimal secondValue) {
		return firstValue.add( secondValue );
	}

	private BigDecimal multiply(BigDecimal value, BigDecimal multiplyFactor) {
		return round( value.multiply( multiplyFactor ) );
	}

	private BigDecimal round(BigDecimal value) {
		return value.setScale( 3, RoundingMode.HALF_EVEN );
	}
}
