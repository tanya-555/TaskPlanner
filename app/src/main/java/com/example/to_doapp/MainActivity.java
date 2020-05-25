package com.example.to_doapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView clickTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        clickTextView = findViewById(R.id.click_tv);
        progressBar.setVisibility(View.VISIBLE);
        initListener();
    }

    private void initListener() {
        clickTextView.setOnClickListener(v -> {
            progressBar.setVisibility(View.GONE);
            Intent intent = new Intent(MainActivity.this, ManageTaskActivity.class);
            startActivity(intent);
            finish();
        });
    }

}
