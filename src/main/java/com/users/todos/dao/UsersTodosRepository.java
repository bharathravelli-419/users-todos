package com.users.todos.dao;

import com.users.todos.entity.UsersTodos;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface UsersTodosRepository extends MongoRepository<UsersTodos, String> {
}
