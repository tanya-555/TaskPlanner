package com.example.to_doapp.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.example.to_doapp.R;
import com.example.to_doapp.contract.AddTaskContract;
import com.example.to_doapp.databinding.AddTaskControllerBinding;
import com.example.to_doapp.db.TaskDatabase;
import com.example.to_doapp.model.TaskModel;
import com.example.to_doapp.presenter.AddTaskPresenter;
import com.hannesdorfmann.mosby3.mvp.conductor.MvpController;

import io.reactivex.disposables.CompositeDisposable;

public class AddTaskController extends MvpController<AddTaskContract.View, AddTaskPresenter>
        implements AddTaskContract.View {


    AddTaskControllerBinding binding;
    CompositeDisposable disposable;
    private String taskName;
    private Bundle bundle;
    private String date;

    public AddTaskController(Bundle bundle) {
        super(bundle);
        this.bundle = bundle;
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_task_controller, container, false);
        disposable = new CompositeDisposable();
        date = bundle.getString("DATE");
        binding.dateTv.setText(date);
        return binding.getRoot();
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        setListener();
    }

    private void setListener() {

        binding.addnow.setOnClickListener(v -> addTask());
    }

    private void addTask() {
        if (("").equals(binding.taskName.getText().toString())) {
            binding.taskName.setError("Task name required!!!");
            binding.taskName.requestFocus();
            return;
        }
        if (binding.taskName.getText().toString().length() > 30) {
            binding.taskName.setError("Character limit exceeded");
            binding.taskName.requestFocus();
            return;
        }
        taskName = binding.taskName.getText().toString();
        TaskModel task = new TaskModel();
        task.setTaskName(taskName);
        task.setStatus("pending");

        //logic for priority radio button
        if (binding.high.isChecked()) {
            task.setPriority(true);
        } else {
            task.setPriority(false);
        }
        task.setDate(date);
        getPresenter().setTaskModel(task);
        getPresenter().addData(TaskDatabase.getInstance(getActivity()));
    }

    @NonNull
    @Override
    public AddTaskPresenter createPresenter() {
        return new AddTaskPresenter();
    }

    @NonNull
    @Override
    public AddTaskPresenter getPresenter() {
        return super.getPresenter();
    }

    @Override
    public void onTaskAdded() {
        Toast.makeText(getActivity(), "Added Task", Toast.LENGTH_LONG).show();
        getRouter().popController(this);
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

}
