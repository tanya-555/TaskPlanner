package com.example.to_doapp.presenter;

import android.content.Context;

import com.example.to_doapp.contract.AppContract;
import com.example.to_doapp.db.TaskDatabase;
import com.example.to_doapp.model.TaskModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AppPresenter extends AppContract.Presenter {

    public AppContract.View view;
    public TaskDatabase database;

    public AppPresenter(Context context) {
        database = TaskDatabase.getInstance(context);
    }

    @Override
    public void loadData() {
        database.taskOperationsDao().getTask().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TaskModel>() {
                    @Override
                    public void accept(TaskModel taskList) throws Exception {
                        view.showData(taskList);
                    }
                });
//        int numberOfRows = database.taskOperationsDao().getNumberOfRows();
//        if(numberOfRows > 0) {
//
//        } else {
//            view.showNoContent();
//        }
    }

    @Override
    public void attachView(AppContract.View view) {

    }

    @Override
    public void detachView(boolean retainInstance) {

    }

    @Override
    public void detachView() {

    }

    @Override
    public void destroy() {

    }
}
