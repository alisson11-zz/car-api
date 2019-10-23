package com.fictiusclean.car.api.services;

import static javax.validation.Validation.buildDefaultValidatorFactory;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.stereotype.Service;

import com.fictiusclean.car.api.exceptions.ValidationException;

@Service
public class ValidationService {

	public <T> void validate(T object) {
		Validator validator = buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<T>> violations = validator.validate( object );
		if (!violations.isEmpty())
			throw new ValidationException( addToErrors( violations ) );
	}

	private <T> List<String> addToErrors(Set<ConstraintViolation<T>> violation) {
		return violation.stream().map( ConstraintViolation::getMessage ).collect( Collectors.toList() );
	}
}
