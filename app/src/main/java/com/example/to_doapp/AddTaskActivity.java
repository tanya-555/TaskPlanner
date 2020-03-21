package com.example.to_doapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.to_doapp.contract.AddTaskContract;
import com.example.to_doapp.databinding.AddTaskControllerBinding;
import com.example.to_doapp.presenter.AddTaskPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;
import com.jakewharton.rxbinding3.view.RxView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import kotlin.Unit;

public class AddTaskActivity extends MvpActivity<AddTaskContract.View, AddTaskPresenter> implements AddTaskContract.View {

    private AddTaskControllerBinding binding;
    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task_controller);
        compositeDisposable = new CompositeDisposable();
        setListener();
    }

    @NonNull
    @Override
    public AddTaskPresenter createPresenter() {
        return null;
    }

    private void setListener() {
        compositeDisposable.add(RxView.clicks(binding.addnow)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Unit>() {
                               @Override
                               public void accept(Unit s)  {

                               }
                           }
                ));
    }
}
