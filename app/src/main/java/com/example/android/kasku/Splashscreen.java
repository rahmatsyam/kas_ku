package com.example.android.kasku;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        //Thread
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(2000);// 1000 ms = 1 detik
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //Intent
                    Intent intent = new Intent(Splashscreen.this, MainActivity.class);
                    startActivity(intent);

                    //close Activity
                    finish();
                }

            }

        };
        timer.start();
    }
}
