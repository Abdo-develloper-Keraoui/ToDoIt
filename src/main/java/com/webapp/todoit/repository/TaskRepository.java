package com.webapp.todoit.repository;

import com.webapp.todoit.entity.Task;
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

    // Custom method: Find non-deleted tasks
    //TODO search for other custom methods i will be needing
    List<Task> findByIsDeletedFalse();

}
