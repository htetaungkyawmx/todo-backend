package org.example.todobackend.exception;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalErrorExceptionHandler {
    @ExceptionHandler(TodoApiException.class)
    public ResponseEntity<ErrorDetails>
    handleTodoException(TodoApiException exception,
                        WebRequest request){
        ErrorDetails errorDetails=new ErrorDetails(
                exception.getMessage(),
                request.getDescription(false),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
