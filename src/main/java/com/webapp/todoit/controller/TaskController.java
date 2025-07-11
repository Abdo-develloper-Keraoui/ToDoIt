// 1. Package declaration
package com.webapp.todoit.controller;

// 2. Essential imports
import java.util.List;

import com.webapp.todoit.entity.Task;
import com.webapp.todoit.entity.TaskStatus;
import com.webapp.todoit.service.TaskService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// 3. Class-level annotations
//Controller (HTTP handler: link between frontend and backend)
@RestController // Marks this as a REST controller (returns data, not views)
@RequestMapping("/api/tasks") // Base URL for all endpoints in this controller
public class TaskController {

    //@Autowired bad constructor injection good!
    private final TaskService taskService;

    public TaskController(TaskService taskService)
    {
        this.taskService = taskService;
    }

    //Create a task and save it to database
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task)
    {
        Task savedTask = taskService.createTask(task);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }

    //Get a specific task by its id
    //Think of should i access a deleted task by its id ?
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id)
    {
        Task requestedTask = taskService.getTaskByID(id);
        return new ResponseEntity<>(requestedTask, HttpStatus.OK);//HttpStatus indicates the element is found and not HttpStatus.FOUND its got other usages
    }

    //Get active tasks (non soft-deleted) for an all tasks view
    @GetMapping("/active")
    public ResponseEntity<List<Task>> getActiveTasks()
    {
        List<Task> allNonDeletedTasks = taskService.getActiveTasks();
        return new ResponseEntity<>(allNonDeletedTasks, HttpStatus.OK);
    }

    //Get Active tasks by their status for specific views
    @GetMapping("/status/{taskStatus}")
    public ResponseEntity<List<Task>> getTasksByStatus(@PathVariable TaskStatus taskStatus)
    {
        List<Task> tasksByTheirStatus = taskService.getActiveTasksByStatus(taskStatus);
        return new ResponseEntity<>(tasksByTheirStatus, HttpStatus.OK);
    }

    //Get soft deleted Tasks for trash view
    @GetMapping("/trash")
    public ResponseEntity<List<Task>> getDeletedTasks()
    {
        List<Task> allDeletedTasks = taskService.getSoftDeletedTasks();
        return new ResponseEntity<>(allDeletedTasks, HttpStatus.OK);
    }

    //Updating logic
    //TODO Update the title
    @PatchMapping("/{id}/title")
    public ResponseEntity<Task> updateTaskTitle(@PathVariable Long id, @RequestParam String newTaskTitle)
    {
        Task updatedTaskWithNewTitle = taskService.updateTaskTitle(id, newTaskTitle);
        return new ResponseEntity<>(updatedTaskWithNewTitle, HttpStatus.OK);
    }
    @PatchMapping("/{id}/description")
    public ResponseEntity<Task> updateTaskDescription(@PathVariable Long id, @RequestParam String newTaskDescription)
    {
        Task updatedTaskWithNewDescription = taskService.updateTaskDescription(id, newTaskDescription);
        return new ResponseEntity<>(updatedTaskWithNewDescription, HttpStatus.OK);
    }

    //TODO Update description
    //Updating the status
    @PatchMapping("/{id}/status")
    public ResponseEntity<Task> updateTaskStatus(@PathVariable Long id, @RequestParam TaskStatus newTaskStatus)
    {
        Task updatedTaskWithNewStatus = taskService.updateTaskStatus(id, newTaskStatus);

        return new ResponseEntity<>(updatedTaskWithNewStatus, HttpStatus.OK);
    }

    ///TODO soft and hard deletion logic
    //(you are still updating either soft delete or retrieve from trash no hard deletion here)

    // Move to trash
    @PatchMapping("/{id}/trash")
    public ResponseEntity<Task> softDeleteTask(@PathVariable Long id)
    {
        Task softDeletedTask = taskService.moveToTrash(id);

        return new ResponseEntity<>(softDeletedTask, HttpStatus.OK);
    }

    // Restore from trash
    @PatchMapping("/{id}/restore")
    public ResponseEntity<Task> restoreTaskFromTrash(@PathVariable Long id)
    {
        Task restoredTaskFromTrash = taskService.restoreFromTrash(id);

        return new ResponseEntity<>(restoredTaskFromTrash, HttpStatus.OK);
    }


    //hard delete item or task
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> hardDeleteTask(@PathVariable Long id)
    {
        taskService.deleteTaskPermanently(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
/*// Controller (HTTP Handler)
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

}*/
    //other way to create a task and save it to database
