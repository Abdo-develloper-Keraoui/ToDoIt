package com.webapp.todoit.exceptions;

public class TaskAlreadyDeletedException extends RuntimeException {
    public TaskAlreadyDeletedException(long id) {
        super("Task by ID" + id + "cannot be modified, because it is deleted");
    }
}
