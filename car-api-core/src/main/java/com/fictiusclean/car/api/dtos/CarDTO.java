package com.fictiusclean.car.api.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonFormat;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {

	private String name;

	private String brand;

	private String model;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private String manufacturingDate;

	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private BigDecimal averageCityConsumption;

	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private BigDecimal averageHighwayConsumption;
}
