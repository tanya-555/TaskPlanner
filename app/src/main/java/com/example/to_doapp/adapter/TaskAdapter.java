package com.example.to_doapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_doapp.R;
import com.example.to_doapp.databinding.CustomViewBinding;
import com.example.to_doapp.event.DeleteTaskEvent;
import com.example.to_doapp.event.UpdateStatusEvent;
import com.example.to_doapp.model.TaskModel;
import com.example.to_doapp.viewholder.TaskViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.List;


public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {
    private List<TaskModel> taskList;
    private CustomViewBinding binding;
    private Context context;

    public TaskAdapter(List<TaskModel> taskList, Context context) {
        this.taskList = taskList;
        this.context = context;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = CustomViewBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new TaskViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskModel task = taskList.get(position);
        holder.bindData(task);
        //check status
        if("completed".equals(taskList.get(position).getStatus())) {
            binding.doneStatus.setVisibility(View.VISIBLE);
        } else {
            binding.doneStatus.setVisibility(View.GONE);
        }
        holder.itemView.findViewById(R.id.delete).setOnClickListener(v ->
                EventBus.getDefault().post(new DeleteTaskEvent(task)));

        //If task is pending then add listener for updating the status to completed
        if("pending".equals(taskList.get(position).getStatus())) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    openDialog(task, position);
                    return true;
                }
            });
        }
    }

    private void openDialog(TaskModel task, int position) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage("Do you want to update this task as completed?");
        alertDialog.setCancelable(false);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EventBus.getDefault().post(new UpdateStatusEvent(task, position));
                        alertDialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

}
