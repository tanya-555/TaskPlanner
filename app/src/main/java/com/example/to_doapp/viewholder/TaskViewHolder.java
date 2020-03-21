package com.example.to_doapp.viewholder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_doapp.databinding.CustomViewBinding;
import com.example.to_doapp.model.TaskModel;

import io.reactivex.Completable;
import io.reactivex.subjects.PublishSubject;

public class TaskViewHolder extends RecyclerView.ViewHolder {

    CustomViewBinding binding;
    private PublishSubject<TaskModel> taskModelPublishSubject = PublishSubject.create();

    public  TaskViewHolder(@NonNull CustomViewBinding binding) {
        super(binding.getRoot());
        this.binding=binding;
    }

    public void bindData(TaskModel taskModel) {
        binding.setMODEL(taskModel);
        binding.executePendingBindings();
    }

    public PublishSubject<TaskModel> getTaskModelPublishSubject() {
        return taskModelPublishSubject;
    }
}
