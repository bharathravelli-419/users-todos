package com.users.todos.controller;

import com.users.todos.dto.RequestDTO;
import com.users.todos.dto.responses.GenericResponse;
import com.users.todos.entity.UsersTodos;
import com.users.todos.exceptions.exceptionClasses.DuplicateTaskException;
import com.users.todos.service.UsersTodosServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class TodosController {

    UsersTodosServiceImpl usersTodosService;

    @PostMapping("/add-newTask")
    public GenericResponse<String> addTask(@RequestParam(name = "userId", defaultValue = "user123") String userId , @RequestBody RequestDTO requestDTO) throws DuplicateTaskException {
        boolean addStatus = usersTodosService.addNewTask(userId, requestDTO.getNewTask());
        return new GenericResponse<>( HttpStatus.CREATED,"added new task :"+requestDTO.getNewTask(),null);
    }

    @PostMapping("/complete-task")
    public ResponseEntity<?> completeATask(@RequestBody RequestDTO requestDTO, @RequestParam(name = "userId", defaultValue = "user123") String userId){
        boolean updatedStatus = usersTodosService.completedOneTask(userId, requestDTO.getExistingTask());
        return updatedStatus ? new ResponseEntity<>("completed Task - SUCCESS",HttpStatus.OK) : new ResponseEntity<>("Could not Complete the Task ", HttpStatus.NOT_MODIFIED);
    }

    @PutMapping("/update-task")
    public ResponseEntity<?> updateTask(@RequestBody RequestDTO requestDTO, @RequestParam(name="userId", defaultValue = "user123") String userId){
        return usersTodosService.modifyTask(userId, requestDTO.getExistingTask(), requestDTO.getNewTask()) ? new ResponseEntity<String>("Updated", HttpStatus.OK): new ResponseEntity<String>("could not update", HttpStatus.NOT_MODIFIED);
    }

    @PostMapping("/undo-task")
    public ResponseEntity<?> undoTask(@RequestBody RequestDTO requestDTO, @RequestParam(name = "userId", defaultValue = "user123") String userId){
        return usersTodosService.undoClosedTask(userId, requestDTO.getExistingTask()) ? new ResponseEntity<>("undo -SUCCESS",HttpStatus.CREATED) : new ResponseEntity<>("undo -UNSUCCESSFUL", HttpStatus.NOT_MODIFIED);
    }

    @GetMapping("/get-user-data")
    public ResponseEntity<UsersTodos> getAllUserData(@RequestParam(name = "userId", defaultValue = "user123") String userId){
        return new ResponseEntity<>(usersTodosService.getAllUserData(userId), HttpStatus.OK);
    }

    @DeleteMapping("/delete-task")
    public ResponseEntity<?> deleteTask(@RequestParam(name = "userId", defaultValue = "user123") String userId, @RequestBody RequestDTO requestDTO){
        usersTodosService.removeTask(userId, requestDTO.getExistingTask());
        return new ResponseEntity<String>("DELETE-SUCCESS", HttpStatus.OK);
    }
}
