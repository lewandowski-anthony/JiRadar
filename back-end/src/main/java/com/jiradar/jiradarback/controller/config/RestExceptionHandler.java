package com.jiradar.jiradarback.controller.config;

import com.jiradar.jiradarback.exception.BusinessException;
import com.jiradar.jiradarback.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Random;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<?> handleException(Exception e) {
		String errorCode = generateHex();
		log.error("Exception caught - code {} : ", errorCode, e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new InternalServerResponseError(e.getClass().getSimpleName(), e.getMessage(), errorCode));
	}

	@ExceptionHandler(value = WebClientResponseException.NotFound.class)
	public ResponseEntity<?> handleWebClientNotFoundException(WebClientResponseException.NotFound e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseError("Ressource Not Found", e.getMessage()));
	}

	@ExceptionHandler(value = NotFoundException.class)
	public ResponseEntity<?> handleNotFoundException(NotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseError("Ressource Not Found", e.getMessage()));
	}

	@ExceptionHandler(value = BusinessException.class)
	public ResponseEntity<?> handleBusinessException(BusinessException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError("Business Error", e.getMessage()));
	}

	@AllArgsConstructor
	@Getter
	public static class ResponseError {

		private final String error;
		private final String message;

	}

	@Getter
	public static class InternalServerResponseError extends ResponseError {

		private final String errorCode;

		public InternalServerResponseError(String error, String message, String errorCode) {
			super(error, message);
			this.errorCode = errorCode;
		}
	}

	private static String generateHex() {
		return String.format("0x%05X", new Random().nextInt(0xFFFFF + 1));
	}
}
