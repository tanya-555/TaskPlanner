package com.example.to_doapp.controller;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.example.to_doapp.R;
import com.example.to_doapp.contract.AddTaskContract;
import com.example.to_doapp.databinding.AddTaskControllerBinding;
import com.example.to_doapp.db.TaskDatabase;
import com.example.to_doapp.model.TaskModel;
import com.example.to_doapp.presenter.AddTaskPresenter;
import com.hannesdorfmann.mosby3.mvp.conductor.MvpController;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.logging.Logger;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import kotlin.Unit;

import static android.content.ContentValues.TAG;

public class AddTaskController extends MvpController<AddTaskContract.View, AddTaskPresenter> implements AddTaskContract.View {


    AddTaskControllerBinding binding;
    CompositeDisposable disposable;
    private String taskName;

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_task_controller,container,  false);
        disposable = new CompositeDisposable();
        return binding.getRoot();
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        setListener();
    }

    private void setListener() {

        binding.addnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });
    }

    private void addTask() {
        if(("").equals(binding.taskName.getText().toString())) {
            binding.taskName.setError("Task name required!!!");
            binding.taskName.requestFocus();
            return;
        }
        taskName = binding.taskName.getText().toString();
        TaskModel task = new TaskModel();
        task.setTaskName(taskName);
        task.setPriority(true);
        getPresenter().loadData(getApplicationContext(), task);
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
        getRouter().popController(this);
    }
}
