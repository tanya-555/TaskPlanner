package com.example.to_doapp.event;

import com.example.to_doapp.model.TaskModel;

public class UpdateStatusEvent {

    public TaskModel taskModel;
    public int position;

    public UpdateStatusEvent(TaskModel taskModel, int position) {
        this.taskModel = taskModel;
        this.position = position;
    }
}
