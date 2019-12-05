package com.sung.testdemo.viewtobitmap;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.sung.testdemo.R;

public class SnapshotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snapshot);

        snapshot();
    }

    private void snapshot(){
        View snapshotView = LayoutInflater.from(this).inflate(R.layout.view_snap_shot_demo, null, false);
        ((ImageView) findViewById(R.id.img)).setImageBitmap(SnapshotUtils.viewSnapshot(snapshotView));
        ((ImageView) findViewById(R.id.img1)).setImageBitmap(SnapshotUtils.viewSnapshot(snapshotView, Color.WHITE,30));
    }
}
