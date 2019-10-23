package com.fictiusclean.car.api.exceptions.handlers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fictiusclean.car.api.exceptions.ValidationException;

@RestController
@ControllerAdvice
public class ValidationExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ValidationException.class)
	public List<String> onError(ValidationException exception) {
		return exception.getErrors();
	}
}

