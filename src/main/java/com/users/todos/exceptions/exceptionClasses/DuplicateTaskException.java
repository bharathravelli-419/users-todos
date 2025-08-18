package com.users.todos.exceptions.exceptionClasses;

public class DuplicateTaskException extends Exception{

    public DuplicateTaskException(String exceptionMessage){
        super(exceptionMessage);
    }
}
