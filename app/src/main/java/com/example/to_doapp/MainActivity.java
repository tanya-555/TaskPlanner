package com.example.to_doapp;

import androidx.appcompat.app.AppCompatActivity;

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
    private CountDownTimer countDownTimer;
    private Router router;
    private int time =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView=findViewById(R.id.imageView);
        progressBar = findViewById(R.id.progressBar);
        textView=findViewById(R.id.header);
        progressBar.setProgress(time);

        router = Conductor.attachRouter(MainActivity.this, (ViewGroup) findViewById(R.id.router), savedInstanceState);
         countDownTimer = new CountDownTimer(5000, 1000) {


            public void onTick(long millisUntilFinished) {

                time++;
                progressBar.setProgress(time*100/(5000/1000));
            }

            public void onFinish() {
                time++;
                progressBar.setProgress(100);
                progressBar.setVisibility(View.GONE);
                imageView.setVisibility(View.GONE);
                textView.setVisibility(View.GONE);

                if(!router.hasRootController()) {
                    router.setRoot(RouterTransaction.with(new MainController()));
                }

            }
        }.start();

    }

    @Override
    public void onBackPressed() {
        if(!router.handleBack()) {
            super.onBackPressed();
        }
    }
}
