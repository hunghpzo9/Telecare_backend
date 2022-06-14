package com.example.telecare.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handlerBadRequestException(BadRequestException ex, WebRequest req){
        ErrorResponse errorResponse = new ErrorResponse(new Date(),ex.getMessage());
        return new ResponseEntity(errorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handlerNotFoundException(NotFoundException ex, WebRequest req){
        ErrorResponse errorResponse = new ErrorResponse(new Date(),ex.getMessage());
        return new ResponseEntity(errorResponse,HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handlerNotFoundException(ResourceNotFoundException ex, WebRequest req){
        ErrorResponse errorResponse = new ErrorResponse(new Date(),ex.getMessage());
        return new ResponseEntity(errorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> handlerUnauthorizedException(UnauthorizedException ex, WebRequest req){
        ErrorResponse errorResponse = new ErrorResponse(new Date(),ex.getMessage());
        return new ResponseEntity(errorResponse,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<?> handlerForbiddenException(ForbiddenException ex, WebRequest req){
        ErrorResponse errorResponse = new ErrorResponse(new Date(),ex.getMessage());
        return new ResponseEntity(errorResponse,HttpStatus.FORBIDDEN);
    }
}
