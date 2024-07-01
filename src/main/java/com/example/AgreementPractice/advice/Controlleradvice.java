package com.example.AgreementPractice.advice;


import com.example.AgreementPractice.exception.EmptyInputException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class Controlleradvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmptyInputException.class)
    public ResponseEntity<Map<String, Object>> handleEmptyInput(EmptyInputException exception) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Input Field is empty");
        response.put("message", "Please fill it");
        response.put("status", HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
@ExceptionHandler(NoSuchElementException.class)
public ResponseEntity<String> handleNosuchElement( NoSuchElementException elementException){

    return new ResponseEntity<>("No such id is found,Please change it", HttpStatus.NOT_FOUND);
}
        @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("errors", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }



}
