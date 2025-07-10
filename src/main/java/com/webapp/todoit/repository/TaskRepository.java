package com.webapp.todoit.repository;

import com.webapp.todoit.entity.Task;
import com.webapp.todoit.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/***
 * Repository (Database Communicator)
 * Why we need it:  Handles all CRUD (Create, Read, Update, Delete) operations
 *       "Give me a magic database helper for Task objects with Long IDs"
 *
 */
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Spring automatically creates these methods:
    // save(), findById(), findAll(), deleteById()

    /// Get task by its status for display
    List<Task> findByStatus(TaskStatus status);

    /// Get tasks that are soft-deleted: deleted == true
    List<Task> findByDeletedIsTrue();

    /// Fin non deleted tasks for all view
    List<Task> findByDeletedIsFalseOrderByUpdatedAt();


}
