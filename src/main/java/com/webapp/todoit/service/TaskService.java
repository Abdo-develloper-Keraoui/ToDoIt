package com.webapp.todoit.service;


import com.webapp.todoit.entity.Task;
import com.webapp.todoit.entity.TaskStatus;
import com.webapp.todoit.exceptions.InvalidStatusTransitionException;
import com.webapp.todoit.exceptions.TaskNotFoundException;
import com.webapp.todoit.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/** *Service Layer (Business Logic)
    *What it is: Where your app's "brain" lives
    *Why you need it:: Handles complex operations before data goes to/from database
**/

// Marks this as business logic component
@Service
public class TaskService {

    @Autowired // Injects the repository
    private TaskRepository repository;

    // CREATE: Save new task
    public Task createTask(Task task) {
        return repository.save(task);
    }

    //READ: Get a task by its ID, otherwise throw an error
    public Task getTaskByID(long id)
    {
        return repository.findById(id)
                .orElseThrow(()->new TaskNotFoundException(id));
    }

    // READ: Get active tasks (non-deleted) (For an all tasks page)
    public List<Task> getActiveTasks() {
        return repository.findByDeletedIsFalseOrderByUpdatedAt();
    }

    //READ: Get active tasks by status
    public List<Task> getActiveTasksByStatus(TaskStatus status)
    {
        return repository.findByStatus(status);
    }

    // READ: Get soft deleted tasks
    public List<Task> getSoftDeletedTasks() {
        return repository.findByDeletedIsTrue();
    }


    /*Updating tasks:
        State transitions (for status)
    * You can change the default status (TO_DO)  to IN_Progress or done
    * you can revert InProgress to TO_DO or mark it as done
    * You can't revert done (the final status) to
    *
        state transitions
    * You can change task title and description as long as it is in To_Do or in-progress
    if it is done, deleted or archived you can't !!
    * */

    // Status change with validation
    /**
     * Updates a task's status with business rule validation
     *
     * Flow: Fetch → Validate → Update → Save
     *
     * @param id - Task ID to update
     * @param newStatus - New status to transition to
     * @return Updated task
     * @throws TaskNotFoundException if task doesn't exist
     * @throws InvalidStatusTransitionException if transition is invalid
     */

    public Task updateTaskStatus(long id, TaskStatus newStatus)
    {
        //we get the current task status
            // we check the validity of the transition
            // we transition or not based on the result

        // Get the task (throws exception if not found)
        Task currentTask = getTaskByID(id);
        // Check if this status change is allowed using enum's business rules
        if(!currentTask.getStatus().isValidTransition(newStatus)) {
            throw new InvalidStatusTransitionException(currentTask.getStatus(), newStatus);
        }
        // Update the status in memory
        currentTask.setStatus(newStatus);
        // Save to database (JPA automatically does UPDATE since task has existing ID)
        return repository.save(currentTask);
    }

    //


   /* public boolean isValidTransition(TasksStatus from, TasksStatus to)
    {
        if(from == to) //Status cannot be the same
        {
            //throw new InvalidStatusTransitionException(from, to);
            //I prefer a boolean over an exception
            return false;
        }
        if(from == TasksStatus.TODO && (to == TasksStatus.IN_PROGRESS  || to == TasksStatus.DONE))
        {
            return true;
        }
        if(from == TasksStatus.IN_PROGRESS && (to == TasksStatus.TODO  || to == TasksStatus.DONE))
        {
            return true;
        }
        if(from == TasksStatus.DONE && (to == TasksStatus.TODO  || to == TasksStatus.IN_PROGRESS))
        {
            return false;
        }
    }
*/
    /// TODO go touch grass
    ///
    /// TODO update task title and description


    // SOFT DELETE: Mark task as deleted
    public boolean moveToTrash(Long id) {
        Optional<Task> task = repository.findById(id);
        if (task.isPresent()) {
            Task t = task.get();
            t.setDeleted(true);
            repository.save(t);
            return true;
        }
        return false;
    }

}
