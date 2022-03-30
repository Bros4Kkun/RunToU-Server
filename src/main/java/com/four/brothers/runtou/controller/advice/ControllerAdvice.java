package com.four.brothers.runtou.controller.advice;

import com.four.brothers.runtou.dto.ErrorDto;
import com.four.brothers.runtou.exception.BadRequestException;
import com.four.brothers.runtou.exception.NoAuthorityException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(IllegalArgumentException.class)
  public ErrorDto.Response badRequestHandle(BadRequestException e) {
    return new ErrorDto.Response(BadRequestException.httpStatus, e.getCode(), e.getErrorMsg());
  }

  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(NoAuthorityException.class)
  public ErrorDto.Response noAuthorityHandle(NoAuthorityException e) {
    return new ErrorDto.Response(NoAuthorityException.httpStatus, e.getCode(), e.getErrorMsg());
  }
}
