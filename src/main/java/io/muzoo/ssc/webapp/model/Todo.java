package io.muzoo.ssc.webapp.model;

public class Todo {
    private int id;
    private int userId;
    private String task;
    private boolean completed;

    public Todo(int id, int userId, String task, boolean completed) {
        this.id = id;
        this.userId = userId;
        this.task = task;
        this.completed = completed;
    }

    public int getId() {

        return id;
    }

    public int getUserId() {

        return userId;
    }

    public String getTask() {

        return task;
    }

    public boolean isCompleted() {

        return completed;
    }
}
