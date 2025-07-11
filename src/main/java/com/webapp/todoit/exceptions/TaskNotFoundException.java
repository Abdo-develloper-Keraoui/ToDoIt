package com.webapp.todoit.exceptions;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long id)
    {
        super("Task By ID: " + id + "not found!");
    }
}
