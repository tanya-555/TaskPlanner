package com.example.to_doapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task")
public class TaskModel {

    @PrimaryKey(autoGenerate = true)
    private int taskId;

    @ColumnInfo(name = "name")
    public String taskName;

    @ColumnInfo(name = "priority")
    public boolean priority;

    @ColumnInfo(name="date")
    public String date;

    @ColumnInfo(name="status")
    public String status;

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }

    public boolean getPriority() {
        return priority;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
