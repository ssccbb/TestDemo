package com.sung.testdemo.autoscrollview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sung.testdemo.R;

public class AutoScrollViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_scroll_view);
        AutoScrollBackgroundView scroollView = findViewById(R.id.sv_background);
        EditText duration = findViewById(R.id.et_duration);

        findViewById(R.id.btn_go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int milles = Integer.parseInt(duration.getText().toString().trim());
                    scroollView.go(milles);
                }catch (Exception e){
                    Toast.makeText(AutoScrollViewActivity.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}