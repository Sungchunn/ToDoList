package io.muzoo.ssc.webapp.model;

import java.sql.Date; // or java.util.Date

public class Todo {
    private int id;
    private int userId;
    private String task;          // or title
    private String description;   // new field
    private Date dueDate;         // new field
    private boolean completed;

    // Constructors
    public Todo(int id, int userId, String task, boolean completed) {
        this.id = id;
        this.userId = userId;
        this.task = task;
        this.completed = completed;
    }

    // Overloaded constructor if you want to handle description/dueDate at creation
    public Todo(int id, int userId, String task, String description, Date dueDate, boolean completed) {
        this.id = id;
        this.userId = userId;
        this.task = task;
        this.description = description;
        this.dueDate = dueDate;
        this.completed = completed;
    }

    // Getters and Setters for all fields
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getTask() { return task; }
    public void setTask(String task) { this.task = task; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}