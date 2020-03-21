package com.example.to_doapp.presenter;

import com.example.to_doapp.contract.AddTaskContract;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

public class AddTaskPresenter extends AddTaskContract.Presenter implements MvpPresenter<AddTaskContract.View> {

    @Override
    public void attachView(AddTaskContract.View view) {

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
