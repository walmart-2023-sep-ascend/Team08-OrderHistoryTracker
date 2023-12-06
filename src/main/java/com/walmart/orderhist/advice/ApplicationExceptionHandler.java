package com.walmart.orderhist.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.walmart.orderhist.constant.ApplicationConstant;
import com.walmart.orderhist.exception.CartNotFoundException;
import com.walmart.orderhist.exception.CartServiceException;
import com.walmart.orderhist.exception.InvalidCartException;
import com.walmart.orderhist.exception.OrderNotFoundException;
import com.walmart.orderhist.exception.OrderServiceException;

@ControllerAdvice
public class ApplicationExceptionHandler {

	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<String> handleOrderNotFoundException(OrderNotFoundException e) {
		return new ResponseEntity<>(ApplicationConstant.ORDER_EXCEPTION + e.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(CartNotFoundException.class)
	public ResponseEntity<String> handleCartNotFoundException(CartNotFoundException e) {
		return new ResponseEntity<>(ApplicationConstant.CART_EXCEPTION + e.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(OrderServiceException.class)
	public ResponseEntity<String> handleOrderServiceException(OrderServiceException e) {
		return new ResponseEntity<>(ApplicationConstant.ORDER_EXCEPTION + e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CartServiceException.class)
	public ResponseEntity<String> handleCartServiceException(CartServiceException e) {
		return new ResponseEntity<>(ApplicationConstant.CART_EXCEPTION + e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidCartException.class)
	public ResponseEntity<String> handleInvalidCartException(InvalidCartException e) {
		return new ResponseEntity<>(ApplicationConstant.INVALID_CART_EXCEPTION + e.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleRunTimeException(Exception e) {
		return new ResponseEntity<>("Internal Server Error ,Please refresh the page ",
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
