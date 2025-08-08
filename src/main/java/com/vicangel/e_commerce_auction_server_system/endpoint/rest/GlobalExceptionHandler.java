package com.vicangel.e_commerce_auction_server_system.endpoint.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice(basePackages = {"com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers"})
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    log.error(ex.getMessage(), ex);
    List<String> errors = new ArrayList<>(ex.getFieldErrors().stream()
                                            .map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage())
                                            .toList());

    List<String> globalErrors = ex.getBindingResult().getGlobalErrors().stream()
      .map(DefaultMessageSourceResolvable::getDefaultMessage)
      .toList();

    errors.addAll(globalErrors);

    final var apiError = new ApiError(errors);
    return buildResponseEntity(BAD_REQUEST, apiError);
  }

  private ResponseEntity<Object> buildResponseEntity(final HttpStatusCode statusCode, final ApiError apiError) {
    return new ResponseEntity<>(apiError, statusCode);
  }

  private record ApiError(List<String> errors) {
  }
}
