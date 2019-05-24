package com.sung.testdemo.autocolumngridview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleAdapter;

import com.sung.testdemo.R;

import java.util.ArrayList;
import java.util.List;

public class AutoColumnGridActivity extends AppCompatActivity {
    private AutoColumnGridRecyclerView grid;
    private Button add;
    private List<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_column_grid);

        grid = findViewById(R.id.grid);
        add = findViewById(R.id.add);

        addData();
        grid.setData(data,true);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.add(String.valueOf(data.size()));
                grid.setData(data,true);
            }
        });
    }

    private void addData() {
        for (int i = 0; i < 4; i++) {
            data.add(String.valueOf(i));
        }
    }
}
