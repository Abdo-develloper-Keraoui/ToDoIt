package com.webapp.todoit.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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



    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TasksStatus status = TasksStatus.TODO;


    //Time of creation of task
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt  = LocalDateTime.now();

    //Time of Updating of task
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt  = LocalDateTime.now();

    //Task deleted or no!
    @Column(name = "is_deleted", nullable = false)
    private boolean deleted = false;

    //Preparing for hard deletion
    @Column(name = "is_archived", nullable = false)
    private boolean archived = false;

    // Constructors ----- constructor overloading

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

    public void setTitle(String title)
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

    //public void set

    public TasksStatus getStatus()
    {
        return status;
    }

    //Time of creation You can only access!!
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    public LocalDateTime getCreatedAt()
    {
        return createdAt;
    }

    //Time of updating you can access and change
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    public LocalDateTime getUpdatedAt()
    {
        return updatedAt;
    }

    /// we don't actually need to see it
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    //Is deleted can be accessed and modified to determine if moved to trash before
    //permanent deletion

    public boolean  isDeleted()
    {
        return deleted;
    }

    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }

    public boolean isArchived(){
        return archived;
    }

    public void setArchived(boolean archived)
    {
        this.archived = archived;
    }

    public String toString()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return "Task{ " +
                "id: " + id + "\n" +
                "title: " + title + "\n" +
                "description: " + description + "\n" +
                "created_at: " + createdAt.format(formatter) + "\n" +
                "isDeleted: " + deleted +
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