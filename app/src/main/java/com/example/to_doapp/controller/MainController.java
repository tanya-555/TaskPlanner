package com.example.to_doapp.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.example.to_doapp.contract.AppContract;
import com.example.to_doapp.databinding.MainControllerBinding;
import com.example.to_doapp.model.TaskModel;
import com.example.to_doapp.presenter.AppPresenter;
import com.hannesdorfmann.mosby3.mvp.conductor.MvpController;
import com.example.to_doapp.R;
import com.jakewharton.rxbinding3.view.RxView;
import java.util.concurrent.TimeUnit;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import kotlin.Unit;

public class MainController extends MvpController<AppContract.View, AppPresenter> implements AppContract.View {

    private CompositeDisposable compositeDisposable;
    private MainControllerBinding binding;

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_controller,container,false);
        compositeDisposable = new CompositeDisposable();
        initListener();
        getPresenter().loadData();
        return binding.getRoot();
    }

    private void initListener() {
        compositeDisposable.add(RxView.clicks(binding.addItem)
        .throttleFirst(1, TimeUnit.SECONDS).delay(1,TimeUnit.SECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<Unit>() {
                       @Override
                       public void accept(Unit s)  {

                       }
                   }
                ));
    }

    public void populateList() {

    }

    @Override
    public void showData(TaskModel taskList) {
        populateList();
    }

    @NonNull
    @Override
    public AppPresenter createPresenter() {

        return new AppPresenter(getApplicationContext());
    }

    public AppPresenter getPresenter() {

        return createPresenter();
    }
}
