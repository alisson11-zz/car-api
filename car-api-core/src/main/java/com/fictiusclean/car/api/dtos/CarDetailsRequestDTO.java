package com.fictiusclean.car.api.dtos;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarDetailsRequestDTO {

	@DecimalMin(value = "0.001", message = "Preço do combustível deve ter valor mínimo de 0.001")
	@NotNull(message = "Preço do combustível é de preenchimento obrigatório")
	private BigDecimal fuelPrice;

	@DecimalMin(value = "0.000", message = "Total de quilômetros percorridos na cidade deve ter valor mínimo de 0.000")
	@NotNull(message = "Total de quilômetros percorridos na cidade é de preenchimento obrigatório")
	private BigDecimal cityRouteKilometers;

	@DecimalMin(value = "0.000", message = "Total de quilômetros percorridos na estrada deve ter valor mínimo de 0.000")
	@NotNull(message = "Total de quilômetros percorridos na estrada é de preenchimento obrigatório")
	private BigDecimal highwayRouteKilometers;
}
