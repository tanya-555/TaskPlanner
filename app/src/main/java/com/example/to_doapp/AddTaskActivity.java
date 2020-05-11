package com.example.to_doapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.example.to_doapp.controller.AddTaskController;
import com.example.to_doapp.controller.MainController;

public class AddTaskActivity extends AppCompatActivity {

    private Router router;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task_activity);
        router = Conductor.attachRouter(AddTaskActivity.this, findViewById(R.id.router), savedInstanceState);
        launchController();
    }

    private void launchController() {
        String selectedDate = getIntent().getStringExtra("SELECTED_DATE");
        Bundle bundle = new Bundle();
        bundle.putString("DATE", selectedDate);
        if(!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(new AddTaskController(bundle)));
        }
    }

    @Override
    public void onBackPressed() {
        if(!router.handleBack()) {
            super.onBackPressed();
        }
    }
}
