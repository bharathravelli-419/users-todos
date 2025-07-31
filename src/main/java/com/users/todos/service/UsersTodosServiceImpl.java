package com.users.todos.service;

import com.users.todos.dao.UsersTodosRepository;
import com.users.todos.entity.UsersTodos;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UsersTodosServiceImpl {

//    @Autowired
    UsersTodosRepository usersTodosRepository;
//    Logger logger = LoggerFactory.getLogger(UsersTodosServiceImpl.class);

    public UsersTodos addUsersTodos(UsersTodos usersTodos){
        log.info("Information received to add {}", usersTodos);
        log.debug("checking for null OpenTodos & CompletedTodos.");
      if(usersTodos.getOpenTodos() == null) usersTodos.setOpenTodos(new ArrayList<>());
      if(usersTodos.getCompletedTodos() == null) usersTodos.setCompletedTodos(new ArrayList<>());
      return usersTodosRepository.save(usersTodos);
    }

    public List<UsersTodos> fetchAllUsersTodos(){
        return usersTodosRepository.findAll();
    }

    public boolean addNewTask(String userId, String newTask){
        Optional<UsersTodos> optionalUserTodos = usersTodosRepository.findById(userId);
        UsersTodos existingUserTodos = optionalUserTodos.orElse(null);

        assert existingUserTodos != null;
        if(!existingUserTodos.getOpenTodos().contains(newTask) && !existingUserTodos.getCompletedTodos().contains(newTask)){
           existingUserTodos.getOpenTodos().add(newTask);
           usersTodosRepository.save(existingUserTodos);
           return true;
        }else{
            log.error("Task with same content already exists in one of the Completed or Open Todos!. Please delete one to add");
            return false;
        }

    }

    public boolean completedOneTask(String userId, String task){
        Optional<UsersTodos> optionalExistingUserData =usersTodosRepository.findById(userId);
        UsersTodos existingUserData = optionalExistingUserData.orElseThrow();
        List<String> updatedTodosList = existingUserData.getOpenTodos().stream().filter(eachTodo -> !eachTodo.equals(task)).collect(Collectors.toList());
        existingUserData.setOpenTodos(updatedTodosList);

        //adding task to completed todos
        if(!existingUserData.getCompletedTodos().contains(task)){
            existingUserData.getCompletedTodos().add(task);
            usersTodosRepository.save(existingUserData);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean modifyTask(String userId, String existingTask, String newTask){
        Optional<UsersTodos> optionalExistingUserData =usersTodosRepository.findById(userId);
        if(optionalExistingUserData.isPresent()){
            UsersTodos existingUserData = optionalExistingUserData.orElseThrow();
            List<String> modifiedOpenTodos = existingUserData.getOpenTodos().stream().map(task -> task.equals(existingTask) ? newTask : task).toList();
            existingUserData.setOpenTodos(modifiedOpenTodos);
            usersTodosRepository.save(existingUserData);
            return true;
        }
        return false;
    }

    public boolean undoClosedTask(String userId, String existingTask){
        Optional<UsersTodos> optionalExistingUserData =usersTodosRepository.findById(userId);
        if(optionalExistingUserData.isPresent()){
            UsersTodos existingUserData = optionalExistingUserData.orElseThrow();
            List<String> modifiedCompletedTodos = existingUserData.getCompletedTodos().stream().filter(task-> !task.equals(existingTask)).toList();
            existingUserData.setCompletedTodos(modifiedCompletedTodos);
            existingUserData.getOpenTodos().add(existingTask);
            usersTodosRepository.save(existingUserData);
            return true;
        }
        return false;
    }

    public UsersTodos getAllUserData(String userId){
        Optional<UsersTodos> optionalExistingUserData =usersTodosRepository.findById(userId);
        if(optionalExistingUserData.isPresent()){
            return optionalExistingUserData.orElseThrow();
        }
        return null;
    }

    public void removeTask(String userId,String task){
        Optional<UsersTodos> optionalExistingUserData =usersTodosRepository.findById(userId);
        if(optionalExistingUserData.isPresent()){
            UsersTodos existingUsersTodos = optionalExistingUserData.orElseThrow();
            List<String> modifiedOpenTodos = existingUsersTodos.getOpenTodos().stream().filter(t -> !t.equals(task)).toList();
            existingUsersTodos.setOpenTodos(modifiedOpenTodos);
            usersTodosRepository.save(existingUsersTodos);
        }

    }
}
