package com.example.to_doapp.viewholder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_doapp.databinding.CustomViewBinding;
import com.example.to_doapp.model.TaskModel;

public class TaskViewHolder extends RecyclerView.ViewHolder {

    CustomViewBinding binding;

    public  TaskViewHolder(@NonNull CustomViewBinding binding) {
        super(binding.getRoot());
        this.binding=binding;
    }

    public void bindData(TaskModel taskModel) {
        binding.setMODEL(taskModel);
        binding.executePendingBindings();
    }
}
