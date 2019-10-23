package com.fictiusclean.car.api.exceptions;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ValidationException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	@Getter
	private final List<String> errors;
}
