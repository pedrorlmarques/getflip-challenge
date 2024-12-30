package com.example.adapter.in.web;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.application.domain.exception.LongURLNotFoundException;

@ControllerAdvice
class ExceptionTranslator extends ResponseEntityExceptionHandler {

	@ExceptionHandler(LongURLNotFoundException.class)
	// used for documentation
	@ResponseStatus(NOT_FOUND)
	public ProblemDetail handleShortURLNotFoundException(LongURLNotFoundException longURLNotFoundException) {
		return ProblemDetail
				.forStatusAndDetail(NOT_FOUND,
				                    longURLNotFoundException.getMessage());
	}
}
