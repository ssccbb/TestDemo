package com.sung.testdemo.countingdown;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.sung.testdemo.R;

public class CountingDownActivity extends AppCompatActivity {
    private CountingDownTimer timer;
    private Button duration;
    private Button start;
    private Button pause;
    private Button stop;

    private int min = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counting_down);
        initView();
        setListener();
        reset();
    }

    private void initView() {
        timer = findViewById(R.id.counting_down);
        duration = findViewById(R.id.duration);
        start = findViewById(R.id.start);
        pause = findViewById(R.id.pause);
        stop = findViewById(R.id.stop);
    }

    private void setListener() {
        timer.addCountingDownListener(this::done);
        duration.setOnClickListener(v -> reset());
        start.setOnClickListener(v -> timer.start());
        pause.setOnClickListener(v -> timer.pause());
        stop.setOnClickListener(v -> timer.cancel());
    }

    private void done() {
        Toast.makeText(this, "计时完成！", Toast.LENGTH_SHORT).show();
    }

    private void reset() {
        min++;
        if (min > 3) min = 1;
        duration.setText(min + "分钟");
        timer.reset(min);
    }
}
