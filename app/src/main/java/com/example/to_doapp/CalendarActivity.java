package com.example.to_doapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.to_doapp.util.CalendarUtil;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private Button continueBtn;
    String selectedDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_activity);
        calendarView = findViewById(R.id.calendar);
        continueBtn = findViewById(R.id.btn_continue);
        selectedDate = CalendarUtil.getToday();
        setDateChangeListener();
        setContinueButtonPressListener();
    }

    private void setDateChangeListener() {
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = String.format("%d - %d - %d", dayOfMonth, month+1, year);
        });
    }

    private void setContinueButtonPressListener() {
        continueBtn.setOnClickListener(v -> {
            if(checkDateValidity()) {
                Intent intent = new Intent(getApplicationContext(), AddTaskActivity.class);
                intent.putExtra("SELECTED_DATE", selectedDate);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean checkDateValidity() {
        if(CalendarUtil.convertStringToMilliseconds(selectedDate) <
                CalendarUtil.convertStringToMilliseconds(CalendarUtil.getCurrentDate())) {
            Toast.makeText(getApplicationContext(), "Please select proper date!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
