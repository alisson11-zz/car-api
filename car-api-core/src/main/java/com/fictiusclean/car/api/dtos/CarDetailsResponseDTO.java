package com.fictiusclean.car.api.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarDetailsResponseDTO {
	private String name;
	private String brand;
	private String model;
	private int year;
	private BigDecimal totalFuelLitersSpentQuantity;
	private BigDecimal totalFuelExpenseValue;
}
