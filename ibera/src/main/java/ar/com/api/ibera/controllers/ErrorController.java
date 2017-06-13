package ar.com.api.ibera.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import ar.com.api.ibera.constants.ViewConstant;

@ControllerAdvice
public class ErrorController {

	@ExceptionHandler(Exception.class)
	public String showInternalServerError(){
		return ViewConstant.ERROR_500;
	}
}
