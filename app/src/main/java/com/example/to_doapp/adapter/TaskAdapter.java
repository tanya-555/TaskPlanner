package com.example.to_doapp.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.to_doapp.databinding.CustomViewBinding;
import com.example.to_doapp.event.DeleteTaskEvent;
import com.example.to_doapp.model.TaskModel;
import com.example.to_doapp.viewholder.TaskViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.List;


public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {
    private List<TaskModel> taskList;
    private CustomViewBinding binding;

    public TaskAdapter(List<TaskModel> taskList, Context context) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = CustomViewBinding.inflate(LayoutInflater.from(parent.getContext()));
        TaskViewHolder taskViewHolder = new TaskViewHolder(binding);
        return taskViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskModel task = taskList.get(position);
        holder.bindData(task);
        binding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DeleteTaskEvent(taskList.get(position)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

}
