package com.webapp.todoit.exceptions;

public class TaskAlreadyDeletedException extends RuntimeException {
    public TaskAlreadyDeletedException(Long id) {
        super("Task by ID" + id + "cannot be modified, because it is deleted");
    }
}
