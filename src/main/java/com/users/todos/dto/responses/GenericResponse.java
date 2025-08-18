package com.users.todos.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
public class GenericResponse<T> {

    private HttpStatus httpStatus;
    private T response;
    private List<String> errorsList;
}
