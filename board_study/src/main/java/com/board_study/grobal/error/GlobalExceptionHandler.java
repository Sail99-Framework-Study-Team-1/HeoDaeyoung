package com.board_study.grobal.error;

import com.board_study.grobal.exception.PasswordValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(PasswordValidationException.class)
	public ResponseEntity<String> PasswordValidationException(PasswordValidationException e){
		log.error(e.getMessage());

		return new ResponseEntity<>(e.getMessage() ,HttpStatus.UNAUTHORIZED);
	}

}
