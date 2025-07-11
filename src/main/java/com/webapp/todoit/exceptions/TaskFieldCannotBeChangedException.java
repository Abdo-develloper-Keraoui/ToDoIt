package com.webapp.todoit.exceptions;

public class TaskFieldCannotBeChangedException extends RuntimeException{
    public TaskFieldCannotBeChangedException(String field)
    {
        super("The Task's " + field + " cannot be changed");
    }
}
