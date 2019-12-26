package com.sung.testdemo.videoframes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.sung.testdemo.R;

public class VideoFetchingLaunchActivity extends AppCompatActivity {
    private TextView mPathDisplay;

    private String mPath = "/storage/emulated/0/tencent/MicroMsg/WeiXin/1576737259580.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_fetching_launch);

        mPathDisplay = findViewById(R.id.tv_path);

        mPathDisplay.setText(mPath);
        findViewById(R.id.tv_path).setOnClickListener(v -> {
            goTo();
        });
    }

    private void goTo() {
        VideoFetchingActivity.luanch(VideoFetchingLaunchActivity.this, mPathDisplay.getText().toString().trim());
    }
}
