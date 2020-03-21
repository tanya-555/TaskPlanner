package com.example.to_doapp.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.to_doapp.databinding.CustomViewBinding;
import com.example.to_doapp.model.TaskModel;
import com.example.to_doapp.viewholder.TaskViewHolder;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {
    private List<TaskModel> taskList;
    private Context context;

    public TaskAdapter(List<TaskModel> taskList, Context context) {
        this.taskList = taskList;
        this.context = context;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CustomViewBinding binding = CustomViewBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new TaskViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskModel task = taskList.get(position);
        holder.bindData(task);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
