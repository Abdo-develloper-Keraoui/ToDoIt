package com.webapp.todoit.service;


import com.webapp.todoit.entity.Task;
import com.webapp.todoit.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/** Service Layer (Business Logic)
What it is: Where your app's "brain" lives

Why you need it: Handles complex operations before data goes to/from database
*/
// Marks this as business logic component
@Service
public class TaskService {

    @Autowired // Injects the repository
    private TaskRepository repository;

    // CREATE: Save new task
    public Task createTask(Task task) {
        return repository.save(task);
    }

    // READ: Get active tasks (non-deleted)
    public List<Task> getActiveTasks() {
        return repository.findByIsDeletedFalse();
    }

    // SOFT DELETE: Mark task as deleted
    public boolean moveToTrash(Long id) {
        Optional<Task> task = repository.findById(id);
        if (task.isPresent()) {
            Task t = task.get();
            t.setIsDeleted(true);
            repository.save(t);
            return true;
        }
        return false;
    }


}
