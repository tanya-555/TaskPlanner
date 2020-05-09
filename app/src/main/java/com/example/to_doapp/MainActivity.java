package com.example.to_doapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.example.to_doapp.controller.MainController;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;
    private TextView clickTextView;
    private CountDownTimer countDownTimer;
    private int time =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView=findViewById(R.id.imageView);
        progressBar = findViewById(R.id.progressBar);
        textView=findViewById(R.id.header);
        clickTextView = findViewById(R.id.click_tv);
        progressBar.setVisibility(View.VISIBLE);
        initListener();



    }

    private void initListener() {
        clickTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(MainActivity.this, ManageTaskActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
