package com.webapp.todoit.exceptions;

import com.webapp.todoit.entity.TaskStatus;

public class InvalidStatusTransitionException extends RuntimeException {
    public InvalidStatusTransitionException(TaskStatus currentStatus, TaskStatus newStatus)
    {
        super("Task status cannot go from " + currentStatus + " to " + newStatus);
    }
}
