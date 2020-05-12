package com.example.to_doapp.controller;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bluelinelabs.conductor.RouterTransaction;
import com.example.to_doapp.CalendarActivity;
import com.example.to_doapp.adapter.TaskAdapter;
import com.example.to_doapp.contract.AppContract;
import com.example.to_doapp.databinding.MainControllerBinding;
import com.example.to_doapp.event.DeleteTaskEvent;
import com.example.to_doapp.event.UpdateStatusEvent;
import com.example.to_doapp.model.TaskModel;
import com.example.to_doapp.presenter.AppPresenter;
import com.hannesdorfmann.mosby3.mvp.conductor.MvpController;
import com.example.to_doapp.R;
import com.jakewharton.rxbinding3.view.RxView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import kotlin.Unit;

public class MainController extends MvpController<AppContract.View, AppPresenter> implements AppContract.View {

    private CompositeDisposable compositeDisposable;
    private MainControllerBinding binding;
    private TaskAdapter adapter;
    private List<TaskModel> taskModelList;

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_controller,container,false);
        compositeDisposable = new CompositeDisposable();
        taskModelList = new ArrayList<>();
        initListener();
        initSwipeRefreshListener();
        registerEventBus();
        return binding.getRoot();
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        fetchData();
    }

    private void fetchData() {
        getPresenter().loadData(getActivity());
    }

    private void initListener() {
        compositeDisposable.add(RxView.clicks(binding.addItem)
        .throttleFirst(1, TimeUnit.SECONDS).delay(1,TimeUnit.SECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<Unit>() {
                       @Override
                       public void accept(Unit s)  {
                           Intent intent = new Intent(getActivity(), CalendarActivity.class);
                           startActivity(intent);
                       }
                   }
                ));
    }

    public void populateList() {
        adapter = new TaskAdapter(taskModelList,getActivity());
        binding.rvItemlist.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvItemlist.setNestedScrollingEnabled(false);
        binding.rvItemlist.setAdapter(adapter);
    }

    @Override
    public void showData(List<TaskModel> taskList) {
        taskModelList = taskList;
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
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    private void unregisterEventBus() {
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe
    public void handleDeleteTask(DeleteTaskEvent deleteTaskEvent) {
        getPresenter().deleteTask(deleteTaskEvent.taskModel.taskName);
        taskModelList.remove(deleteTaskEvent.taskModel);
        adapter.notifyDataSetChanged();
    }

    @Subscribe
    public void handleUpdateStatus(UpdateStatusEvent updateStatusEvent) {
        getPresenter().updateStatus(updateStatusEvent.taskModel);
        taskModelList.set(updateStatusEvent.position, updateStatusEvent.taskModel);
        adapter.notifyItemChanged(updateStatusEvent.position);
    }

    private void initSwipeRefreshListener() {
        binding.swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData();
                binding.swiperefresh.setRefreshing(false);
            }
        });
    }

}
