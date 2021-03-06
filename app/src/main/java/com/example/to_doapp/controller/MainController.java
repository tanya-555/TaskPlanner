package com.example.to_doapp.controller;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.to_doapp.CalendarActivity;
import com.example.to_doapp.R;
import com.example.to_doapp.adapter.TaskAdapter;
import com.example.to_doapp.contract.AppContract;
import com.example.to_doapp.databinding.MainControllerBinding;
import com.example.to_doapp.db.TaskDatabase;
import com.example.to_doapp.di.DaggerSharedPrefComponent;
import com.example.to_doapp.di.SharedPrefModule;
import com.example.to_doapp.event.DeleteTaskEvent;
import com.example.to_doapp.event.UpdateStatusEvent;
import com.example.to_doapp.model.TaskModel;
import com.example.to_doapp.presenter.AppPresenter;
import com.hannesdorfmann.mosby3.mvp.conductor.MvpController;
import com.jakewharton.rxbinding3.view.RxView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class MainController extends MvpController<AppContract.View, AppPresenter> implements AppContract.View {

    private CompositeDisposable compositeDisposable;
    private MainControllerBinding binding;
    private TaskAdapter adapter;
    private List<TaskModel> taskModelList;

    @Inject
    SharedPreferences sharedPreferences;

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_controller, container, false);
        compositeDisposable = new CompositeDisposable();
        taskModelList = new ArrayList<>();
        DaggerSharedPrefComponent.builder().sharedPrefModule(new SharedPrefModule(getActivity())).
                build().inject(this);
        initSharedPreferences();
        handleInfoDialog();
        initListener();
        initSwipeRefreshListener();
        registerEventBus();
        return binding.getRoot();
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        binding.progressBar.setVisibility(View.VISIBLE);
        fetchData();
    }

    private void fetchData() {
        getPresenter().setTaskModelList(taskModelList);
        getPresenter().loadData(TaskDatabase.getInstance(getActivity()));
    }

    private void initListener() {
        compositeDisposable.add(RxView.clicks(binding.addItem)
                .throttleFirst(1, TimeUnit.SECONDS).delay(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                            Intent intent = new Intent(getActivity(), CalendarActivity.class);
                            startActivity(intent);
                        }
                ));
    }

    public void populateList() {
        adapter = new TaskAdapter(taskModelList, getActivity());
        binding.rvItemlist.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvItemlist.setNestedScrollingEnabled(false);
        binding.rvItemlist.setAdapter(adapter);
    }

    @Override
    public void showData(List<TaskModel> taskList) {
        binding.progressBar.setVisibility(View.GONE);
        taskModelList = taskList;
        checkForEmptyList();
        populateList();
    }

    @NonNull
    @Override
    public AppPresenter createPresenter() {

        return new AppPresenter();
    }

    public AppPresenter getPresenter() {

        return super.getPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterEventBus();
    }

    private void registerEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    private void unregisterEventBus() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe
    public void handleDeleteTask(DeleteTaskEvent deleteTaskEvent) {
        getPresenter().deleteTask(deleteTaskEvent.taskModel.taskName);
        Toast.makeText(getActivity(), "Task deleted successfully!", Toast.LENGTH_LONG).show();
        taskModelList.remove(deleteTaskEvent.taskModel);
        adapter.notifyDataSetChanged();
        checkForEmptyList();
    }

    @Subscribe
    public void handleUpdateStatus(UpdateStatusEvent updateStatusEvent) {
        getPresenter().updateStatus(updateStatusEvent.taskModel);
        Toast.makeText(getActivity(), "Task updated successfully! Swipe to Refresh", Toast.LENGTH_LONG).show();
        taskModelList.set(updateStatusEvent.position, updateStatusEvent.taskModel);
        adapter.notifyItemChanged(updateStatusEvent.position);
    }

    private void initSwipeRefreshListener() {
        binding.swiperefresh.setOnRefreshListener(() -> {
            fetchData();
            binding.swiperefresh.setRefreshing(false);
        });
    }

    private void checkForEmptyList() {
        if (taskModelList.size() == 0) {
            binding.swiperefresh.setVisibility(View.GONE);
            binding.errorView.setVisibility(View.VISIBLE);
        } else {
            binding.swiperefresh.setVisibility(View.VISIBLE);
            binding.errorView.setVisibility(View.GONE);
        }
    }

    private void initSharedPreferences() {
        if (!sharedPreferences.contains("SHOW_INFO_DIALOG")) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("SHOW_INFO_DIALOG", "yes");
            editor.apply();
        }
    }

    private void handleInfoDialog() {
        if ("yes".equals(sharedPreferences.getString("SHOW_INFO_DIALOG", ""))) {
            showInfoDialog();
        }
    }

    private void showInfoDialog() {
        Dialog dialog = new Dialog(Objects.requireNonNull(getActivity()));
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.info_dialog);
        Button okBtn = dialog.findViewById(R.id.ok_btn);
        CheckBox checkBox = dialog.findViewById(R.id.checkbox);
        okBtn.setOnClickListener(v -> {
            setCheckBoxState(checkBox);
            dialog.dismiss();
        });
        dialog.show();
    }

    private void setCheckBoxState(CheckBox checkBox) {
        if (checkBox.isChecked()) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("SHOW_INFO_DIALOG", "no");
            editor.apply();
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("SHOW_INFO_DIALOG", "yes");
            editor.apply();
        }
    }
}
