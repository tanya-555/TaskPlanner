package com.example.to_doapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.example.to_doapp.controller.AddTaskController;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;
    String selectedDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_activity);
        calendarView = findViewById(R.id.calendar);
        setListener();
    }

    private void setListener() {
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = String.format("%d - %d - %d", dayOfMonth, month, year);
                launchActivity();
            }
        });
    }

    private void launchActivity() {
        Intent intent = new Intent(getApplicationContext(), AddTaskActivity.class);
        intent.putExtra("SELECTED_DATE", selectedDate);
        startActivity(intent);
        finish();
    }
}
