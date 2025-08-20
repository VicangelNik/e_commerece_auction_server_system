package com.vicangel.e_commerce_auction_server_system.endpoint.rest;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;

import com.vicangel.e_commerce_auction_server_system.core.error.ItemCategoryException;
import com.vicangel.e_commerce_auction_server_system.core.error.RoleNotValidException;
import com.vicangel.e_commerce_auction_server_system.core.error.UserException;
import com.vicangel.e_commerce_auction_server_system.core.error.UserIdNotFoundException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path.Node;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice(basePackages = {"com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers"})
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(value = MethodArgumentNotValidException.class)
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

  @ExceptionHandler(value = {
    SQLIntegrityConstraintViolationException.class,
    RoleNotValidException.class,
    UserIdNotFoundException.class})
  protected ResponseEntity<Object> handleRequestInputException(
    Exception ex) {
    log.error(ex.getMessage(), ex);
    final var apiError = new ApiError(List.of(ex.getMessage()));
    return buildResponseEntity(BAD_REQUEST, apiError);
  }

  /**
   *
   * @implNote if getConstraintViolations are not handled like this the message will be like -> updateUser.id: must be greater than 0
   *   however, the desired result is -> id -> must be greater than 0
   */
  @ExceptionHandler(ConstraintViolationException.class)
  protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
    final List<String> errors = ex.getConstraintViolations().stream().map(
      violation ->
        StreamSupport.stream(violation.getPropertyPath().spliterator(), false)
          .reduce((first, second) -> second)
          .map(Node::getName)
          .orElse("") + " " + violation.getMessage()
    ).toList();

    log.error(ex.getMessage(), ex);
    final var apiError = new ApiError(errors);
    return buildResponseEntity(BAD_REQUEST, apiError);
  }

  @ExceptionHandler(value = {UserException.class, ItemCategoryException.class})
  protected ResponseEntity<Object> handleBusinessException(RuntimeException ex) {
    log.error(ex.getMessage(), ex);
    final var apiError = new ApiError(List.of(ex.getMessage()));
    return buildResponseEntity(INTERNAL_SERVER_ERROR, apiError);
  }

  private ResponseEntity<Object> buildResponseEntity(final HttpStatusCode statusCode, final ApiError apiError) {
    return new ResponseEntity<>(apiError, statusCode);
  }

  private record ApiError(List<String> errors) {
  }

  @ExceptionHandler({Exception.class})
  protected ResponseEntity<Object> handleException(Exception ex, HandlerMethod handlerMethod) {
    final String errorMsg =
      String.format(
        "Exception occurred in method: %s of class: %s, Exception message: %s",
        handlerMethod.getMethod().getName(),
        handlerMethod.getBeanType().getName(),
        ex.getMessage()
      );
    log.error(errorMsg, ex);

    if (ex.getMessage() != null) {
      final var apiError = new ApiError(List.of(ex.getMessage()));
      return buildResponseEntity(INTERNAL_SERVER_ERROR, apiError);
    }
    return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
  }
}
