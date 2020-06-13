package com.codenation.curso.central.error.controls;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.codenation.curso.central.error.exceptions.ResourceNotFoundException;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {
@ExceptionHandler(MethodArgumentNotValidException.class)
@ResponseStatus(HttpStatus.BAD_REQUEST)
@ResponseBody
public String handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
	return e.getBindingResult().getFieldError().getField()+"-"+ e.getBindingResult().getFieldError().getDefaultMessage();
}
// erro interno  do servidor divisao por zero e um exemplo
@ExceptionHandler(Exception.class)
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
@ResponseBody
public String handleException(Exception e) {
	return "Internal server error"+ e.getMessage();
}

@ExceptionHandler(ResourceNotFoundException.class)
@ResponseStatus(HttpStatus.NOT_FOUND)
@ResponseBody

public String handleResourceNotFoundException(ResourceNotFoundException e) {
	return e.getMessage();
}
}
