package com.users.todos.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "users-todos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersTodos {

    @Id
    private String id;
    private String name;
    private List<String> openTodos;
    private List<String> completedTodos;
//    private Date createdDate;

}
