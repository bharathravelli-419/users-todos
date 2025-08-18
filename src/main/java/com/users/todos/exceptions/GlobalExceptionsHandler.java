package com.users.todos.exceptions;

import com.users.todos.dto.responses.GenericResponse;
import com.users.todos.exceptions.exceptionClasses.DuplicateTaskException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionsHandler {

    @ExceptionHandler(value = {com.users.todos.exceptions.exceptionClasses.DuplicateTaskException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GenericResponse<?> duplicateTaskExceptionHandler(DuplicateTaskException exception){
        List<String> errorsList = new ArrayList<>();
        errorsList.add(exception.getMessage());
        return GenericResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .errorsList(errorsList)
                .response(null)
                .build();
    }
}
