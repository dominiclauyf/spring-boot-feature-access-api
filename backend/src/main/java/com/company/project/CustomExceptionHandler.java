package com.company.project;

import java.util.HashMap;
import java.util.Map;
import javax.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    // This method get triggered whenever there is MethodArgumentNotValidException exception.
    // It shows only the user friendly error message.
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException exception) {

        Map<String, String> errorMap = new HashMap<>();
        exception
                .getBindingResult()
                .getFieldErrors()
                .forEach(
                        error -> {
                            errorMap.put(error.getField(), error.getDefaultMessage());
                        });
        return errorMap;
    }

    // It shows only the user friendly error message.
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ValidationException.class)
    public Map<String, String> handleValidationException(ValidationException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", exception.getMessage());
        return errorMap;
    }
}
