package com.webapp.todoit.controller;

import com.webapp.todoit.entity.Task;
import com.webapp.todoit.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controller (HTTP Handler)
@RestController // Handles HTTP requests
@RequestMapping("/api/tasks") // Base URL
public class TaskController {
    @Autowired
    private TaskService service;

    // POST: Create task
    // Endpoint: POST http://localhost:8081/api/tasks
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@RequestBody Task task) {
        return service.createTask(task);
    }

    // GET: Get active tasks
    // Endpoint: GET http://localhost:8081/api/tasks
    @GetMapping
    public List<Task> getActiveTasks() {
        return service.getActiveTasks();
    }

    // DELETE: Move to trash (soft delete)
    // Endpoint: DELETE http://localhost:8081/api/tasks/{id}
    @DeleteMapping("/{id}")
    public boolean moveToTrash(@PathVariable Long id) {
        return service.moveToTrash(id);
    }

}
