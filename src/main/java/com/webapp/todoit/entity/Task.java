package com.webapp.todoit.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "task")//Table Name in sqlite
public class Task {
    //Unique Identifier for Each Task
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //The title of each task
    @Column(nullable = false)
    private String title;

    //The description of each task
    @Column(columnDefinition = "TEXT")
    private String description;

    //Time of creation of task
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt  = LocalDateTime.now();

    //Task deleted or no!
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    // Constructors  constructor overloading

    public Task() {}

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }

    //Making Setters and getters
    //For Id, you can only access it, not change it!!
    public Long getId() {
        return id;
    }

    //Title you can access and change
    public String getTitle()
    {
        return title;
    }

    public void SetTitle(String title)
    {
        this.title = title;
    }

    //Description you can change and access both

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String Description)
    {
        this.description = Description;
    }

    //Time of creation You can only access!!
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    public LocalDateTime getCreatedAt()
    {
        return createdAt;
    }

    //Is deleted can be accessed and modified to determine if moved to trash before
    //permanent deletion

    public boolean getIsDeleted()
    {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted)
    {
        this.isDeleted = isDeleted;
    }

    public String toString()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return "Task{ " +
                "id: " + id + "\n" +
                "title: " + title + "\n" +
                "description: " + description + "\n" +
                "created_at: " + createdAt.format(formatter) + "\n" +
                "isDeleted: " + isDeleted +
                "}";
    }






}

/***
 *
 * import jakarta.persistence.*;
 * import java.time.LocalDateTime;
 *
 * @Entity
 * @Table(name = "todos") // This will be our table name in SQLite
 * public class Todo {
 *
 *     // Unique identifier for each todo
 *     @Id
 *     @GeneratedValue(strategy = GenerationType.IDENTITY)
 *     private Long id;
 *
 *     // Title of the todo (required field)
 *     @Column(nullable = false)
 *     private String title;
 *
 *     // Description (optional field)
 *     @Column(columnDefinition = "TEXT")
 *     private String description;
 *
 *     // Track creation time automatically
 *     private LocalDateTime createdAt = LocalDateTime.now();
 *
 *     // Deletion flag with default value
 *     private Boolean isDeleted = false;
 *
 *     // Default constructor (required by JPA)
 *     public Todo() {
 *     }
 *
 *     // Constructor for creating new todos
 *     public Todo(String title, String description) {
 *         this.title = title;
 *         this.description = description;
 *     }
 *
 *     // --- GETTERS AND SETTERS ---
 *     // We need these for JPA to work properly
 *
 *     public Long getId() {
 *         return id;
 *     }
 *
 *     public String getTitle() {
 *         return title;
 *     }
 *
 *     public void setTitle(String title) {
 *         this.title = title;
 *     }
 *
 *     public String getDescription() {
 *         return description;
 *     }
 *
 *     public void setDescription(String description) {
 *         this.description = description;
 *     }
 *
 *     public LocalDateTime getCreatedAt() {
 *         return createdAt;
 *     }
 *
 *     public Boolean getIsDeleted() {
 *         return isDeleted;
 *     }
 *
 *     public void setIsDeleted(Boolean isDeleted) {
 *         this.isDeleted = isDeleted;
 *     }
 *
 *     // Helpful for debugging
 *     @Override
 *     public String toString() {
 *         return "Todo{" +
 *                 "id=" + id +
 *                 ", title='" + title + '\'' +
 *                 ", description='" + description + '\'' +
 *                 ", createdAt=" + createdAt +
 *                 ", isDeleted=" + isDeleted +
 *                 '}';
 *     }
 * }
 */