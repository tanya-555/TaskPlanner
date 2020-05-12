package com.example.to_doapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = String.format("%d - %d - %d", dayOfMonth, month, year);
            launchActivity();
        });
    }

    private void launchActivity() {
        Intent intent = new Intent(getApplicationContext(), AddTaskActivity.class);
        intent.putExtra("SELECTED_DATE", selectedDate);
        startActivity(intent);
        finish();
    }
}
