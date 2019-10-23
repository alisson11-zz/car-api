package com.fictiusclean.car.api.mappers;

public interface RequestMapper<E, D> {

	E toEntity(D dto);

	D toDTO(E entity);

}
