package com.jiradar.jiradarback.controller.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Random;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<?> handleException(Exception e) {
		String errorCode = generateHex();
		log.error("Exception caught - code {} : ", errorCode, e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseError(e.getClass().getSimpleName(), e.getMessage(), errorCode));
	}

	public record ResponseError(String error, String message, String errorCode) {
	}

	private static String generateHex() {
		return String.format("0x%05X", new Random().nextInt(0xFFFFF + 1));
	}
}
