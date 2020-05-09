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

import io.reactivex.subjects.PublishSubject;

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {
    private List<TaskModel> taskList;
    private Context context;
    private PublishSubject<TaskModel> taskClickPublishSubject;
    private CustomViewBinding binding;

    public TaskAdapter(List<TaskModel> taskList, Context context) {
        this.taskList = taskList;
        this.context = context;
        taskClickPublishSubject = PublishSubject.create();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = CustomViewBinding.inflate(LayoutInflater.from(parent.getContext()));
        TaskViewHolder taskViewHolder = new TaskViewHolder(binding);
        registerPublishSubject(taskViewHolder);
        return taskViewHolder;
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

    private void registerPublishSubject(TaskViewHolder viewHolder) {
        viewHolder.getTaskModelPublishSubject().subscribe(taskClickPublishSubject);
    }

    public PublishSubject<TaskModel> getTaskClickPublishSubject() {
        return  taskClickPublishSubject;
    }
}
