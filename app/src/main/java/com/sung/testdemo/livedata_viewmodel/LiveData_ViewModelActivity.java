package com.sung.testdemo.livedata_viewmodel;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;

import com.sung.testdemo.R;

public class LiveData_ViewModelActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private ProgressModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_data__view_model);

        SeekBar domain = findViewById(R.id.sb_domain);
        final SeekBar observer = findViewById(R.id.sb_obsever);

        domain.setOnSeekBarChangeListener(this);
        observer.setOnSeekBarChangeListener(this);

        model = ViewModelProviders.of(this).get(ProgressModel.class);
        Observer<Integer> progress = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                observer.setProgress(integer);
            }
        };
        model.getProgress().observe(this,progress);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar.getId() == R.id.sb_domain){
            model.getProgress().postValue(progress);
        }

        if (seekBar.getId() == R.id.sb_obsever){

        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
