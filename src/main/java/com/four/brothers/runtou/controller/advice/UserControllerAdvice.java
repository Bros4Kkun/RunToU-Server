package com.four.brothers.runtou.controller.advice;

import com.four.brothers.runtou.dto.ErrorDto;
import com.four.brothers.runtou.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserControllerAdvice {
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(IllegalArgumentException.class)
  public ErrorDto.Response badRequestHandle(BadRequestException e) {
    return new ErrorDto.Response(BadRequestException.httpStatus, e.getCode(), e.getErrorMsg());
  }
}
