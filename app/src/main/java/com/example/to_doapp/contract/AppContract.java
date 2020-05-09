package com.example.to_doapp.contract;

import android.content.Context;

import com.example.to_doapp.model.TaskModel;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;

public interface AppContract {

    interface View extends MvpView {
        void showData(List<TaskModel> taskModelList);
    }

    abstract class Presenter implements MvpPresenter<View> {
        public abstract void loadData(Context context);
    }
}
