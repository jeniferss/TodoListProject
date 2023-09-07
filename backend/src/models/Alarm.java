package models;

import java.time.LocalDateTime;

public class Alarm {
    private int id;
    private int taskId;
    private LocalDateTime dateTime;

    @Override
    public String toString() {
        return id + ";" + taskId + ";" + dateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDate(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
