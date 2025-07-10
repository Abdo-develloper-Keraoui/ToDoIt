package com.webapp.todoit.exceptions;

import com.webapp.todoit.entity.TasksStatus;

public class InvalidStatusTransitionException extends RuntimeException {
    public InvalidStatusTransitionException(TasksStatus currentStatus, TasksStatus newStatus)
    {
        super("Task status cannot go from " + currentStatus + " to " + newStatus);
    }
}
