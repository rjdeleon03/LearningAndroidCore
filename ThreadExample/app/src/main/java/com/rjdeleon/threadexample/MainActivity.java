package com.rjdeleon.threadexample;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    
    private static final String TAG = "MainActivity";
    private Button mStartButton;

    private volatile boolean stopThread = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStartButton = findViewById(R.id.startButton);
    }

    public void startThread(View view) {
        stopThread = false;
        ExampleThread thread = new ExampleThread(20);
        thread.start();
    }

    public void stopThread(View view) {
        stopThread = true;
    }

    class ExampleThread extends Thread {

        int seconds;

        ExampleThread(int seconds) {
            this.seconds = seconds;
        }

        @Override
        public void run() {
            for (int i = 0; i < seconds; i++) {

                if (stopThread) {
                    Log.wtf(TAG, "Thread stopped!");
                    return;
                }

                Log.wtf(TAG, "startThread: " + i);
                if (i == 5) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            mStartButton.setText("50%");
                        }
                    });
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
