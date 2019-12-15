package com.sung.testdemo.orientation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sung.testdemo.R;

public class Orientation2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orientation2);

        View layout1 = findViewById(R.id.layout1);
        View layout2 = findViewById(R.id.layout2);

        findViewById(R.id.close).setOnClickListener(view -> Orientation2Activity.this.finish());

        findViewById(R.id.hide).setOnClickListener(view -> {
            boolean a = layout1.getVisibility() == View.VISIBLE;
            boolean b = layout2.getVisibility() == View.VISIBLE;
            if (a && b) {
                layout1.setVisibility(View.GONE);
            } else if (!a && b) {
                layout2.setVisibility(View.GONE);
            } else if (!a && !b) {
                layout1.setVisibility(View.VISIBLE);
                layout2.setVisibility(View.VISIBLE);
            }
        });
    }
}
