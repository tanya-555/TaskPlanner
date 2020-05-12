package com.example.to_doapp.event;

import com.example.to_doapp.model.TaskModel;

public class DeleteTaskEvent {

    public TaskModel taskModel;

    public DeleteTaskEvent(TaskModel taskModel) {
        this.taskModel = taskModel;
    }
}
