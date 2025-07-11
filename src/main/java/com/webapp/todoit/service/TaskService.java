package com.webapp.todoit.service;


import com.webapp.todoit.entity.Task;
import com.webapp.todoit.entity.TaskStatus;
import com.webapp.todoit.exceptions.*;
import com.webapp.todoit.repository.TaskRepository;

import org.springframework.stereotype.Service;

import java.util.List;

/** *Service Layer (Business Logic)
    *What it is: Where your app's "brain" lives
    *Why you need it:: Handles complex operations before data goes to/from database
**/

// Marks this as business logic component
@Service
public class TaskService {

    // Injects the repository
    //constructor injection >>>> @Autowired field injection
    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository)
    {
        this.taskRepository = taskRepository;
    }

    // CREATE: Save new task
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    //READ: Get a task by its ID, otherwise throw an error
    public Task getTaskByID(Long id)
    {
        return taskRepository.findById(id)
                .orElseThrow(()->new TaskNotFoundException(id));
    }

    // READ: Get active tasks (non-deleted) (For an all tasks page)
    public List<Task> getActiveTasks() {
        return taskRepository.findByDeletedIsFalseOrderByUpdatedAt();
    }

    //READ: Get active tasks by status
    public List<Task> getActiveTasksByStatus(TaskStatus status)
    {
        return taskRepository.findByStatusAndNotDeleted(status);
    }

    // READ: Get soft deleted tasks
    public List<Task> getSoftDeletedTasks() {
        return taskRepository.findByDeletedIsTrue();
    }


    /*Updating tasks:
        State transitions (for status)
    * You can change the default status (TO_DO)  to IN_Progress or done
    * you can revert InProgress to TO_DO or mark it as done
    * You can't revert done (the final status) to
    *
        state transitions
    * You can change task title and description as Long as it is in To_Do or in-progress
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

    public Task updateTaskStatus(Long id, TaskStatus newStatus)
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
        return taskRepository.save(currentTask);
    }

    /// TODO update task title and description

    public Task updateTaskTitle(Long id, String newTitle)
    {
        Task currentTask = getTaskByID(id);

        if(!currentTask.getStatus().isFieldEditable() || currentTask.isDeleted() || currentTask.isArchived())
        {
            throw new TaskFieldCannotBeChangedException("Title");
        }

        if(currentTask.getTitle().equals(newTitle))
        {
            return currentTask;
        }

        currentTask.setTitle(newTitle);

        return taskRepository.save(currentTask);
    }

    /// Update description
    public Task updateTaskDescription(Long id, String newDescription)
    {
        Task currentTask = getTaskByID(id);

        if(!currentTask.getStatus().isFieldEditable() || currentTask.isDeleted() || currentTask.isArchived())
        {
            throw new TaskFieldCannotBeChangedException("Description");
        }

        if(currentTask.getDescription().equals(newDescription))
        {
            return currentTask;
        }

        currentTask.setDescription(newDescription);

        return taskRepository.save(currentTask);
    }


    // SOFT DELETE Logic: Mark task as deleted
    //TODO Add archiving logic

    public Task moveToTrash(Long id) {
        Task currentTask = getTaskByID(id);

        if (currentTask.isDeleted()) {//|| currentTask.isArchived()) {
            throw new TaskAlreadyDeletedException(id);
        }

        currentTask.setDeleted(true);
        return taskRepository.save(currentTask);
    }

    /***
     * Restoring requires the task to be deleted!
     * @param id
     * @return
     */
    public Task restoreFromTrash(Long id)
    {
        Task currentTask = getTaskByID(id);
        if(!currentTask.isDeleted())
        {
            return currentTask;
        }

        currentTask.setDeleted(false);
        return taskRepository.save(currentTask);
    }

    public void deleteTaskPermanently(Long id)
    {
        Task currentTask = getTaskByID(id);
        if(!currentTask.isDeleted()) {
            throw new TaskIsNotInTrashException(id);

        }
        taskRepository.deleteById(id);
    }
}
