package com.fictiusclean.car.api.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import com.fictiusclean.car.api.dtos.CarDTO;
import com.fictiusclean.car.api.entities.Car;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CarMapper extends RequestMapper<Car, CarDTO> {

	@Mappings(
			@Mapping(source = "manufacturingDate", target = "manufacturingDate", dateFormat = "dd/MM/yyyy")
	)
	Car toEntity(CarDTO dto);

	@InheritInverseConfiguration
	CarDTO toDTO(Car entity);

}
