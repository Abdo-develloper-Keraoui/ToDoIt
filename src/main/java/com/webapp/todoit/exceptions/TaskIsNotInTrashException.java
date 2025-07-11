package com.webapp.todoit.exceptions;

public class TaskIsNotInTrashException extends RuntimeException{
    public TaskIsNotInTrashException(Long id)
    {
        super("Task by id = " + id + "is not in trash, so it cannot be deleted permanently");
    }
}
