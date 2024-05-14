package com.legal.hold.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.legal.hold.entity.LegalHoldError;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class LegalHoldResponseEntiryExceptionHandler extends ResponseEntityExceptionHandler {

//	@ExceptionHandler(value= {RecordNotFoundException.class})
//	protected ResponseEntity<Object> recordNotFoundException(RecordNotFoundException ex){
//		String message="No record found";
//		LegalHoldError error=new LegalHoldError(HttpStatus.NOT_FOUND,message,ex.getLocalizedMessage(),HttpStatus.NOT_FOUND.value());
//		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
//	}

	@ExceptionHandler(value = { RecordNotFoundException.class })
	protected ResponseEntity<Object> recordNotFoundException(RecordNotFoundException ex) {
		String message = "No record found";
		LegalHoldError error = new LegalHoldError(HttpStatus.NOT_FOUND.value(), message);
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

}
