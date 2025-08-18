package com.users.todos.controller;

import com.users.todos.dto.RequestDTO;
import com.users.todos.dto.responses.GenericResponse;
import com.users.todos.entity.UsersTodos;
import com.users.todos.service.UsersTodosServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class UsersController {

    @Autowired
    private UsersTodosServiceImpl usersTodosService;

    @PostMapping("/add-users-todos")
    public GenericResponse<UsersTodos> addUsersTodos(@RequestBody UsersTodos usersTodos){
        return new GenericResponse<>(HttpStatus.CREATED,usersTodosService.addUsersTodos(usersTodos),null);
    }

    @GetMapping("/get-all-users-todos")
    public GenericResponse<List<UsersTodos>> getAllUsersTodos(){
        return new GenericResponse<>(HttpStatus.OK,usersTodosService.fetchAllUsersTodos(), null);
    }

}

