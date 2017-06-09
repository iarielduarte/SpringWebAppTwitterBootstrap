package ar.com.api.ibera.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorController {

	public static final String IS_ERROR = "error/500";
	
	@ExceptionHandler(Exception.class)
	public String showInternalServerError(){
		return IS_ERROR;
	}
}
