package com.webapp.todoit.exceptions;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(long id)
    {
        super("Task By ID: " + id + "not found!");
    }
}
