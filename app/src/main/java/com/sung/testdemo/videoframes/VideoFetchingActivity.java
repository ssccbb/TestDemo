package com.sung.testdemo.videoframes;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.sung.testdemo.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VideoFetchingActivity extends AppCompatActivity {
    private static final String KEY_VIDEO_URL = "key_video_url";

    private String mVideoPath;

    private VideoFetchingSeekbar mFramesSeekbar;
    private ImageView mSelectPreview;

    private VideoHelper mHelper;

    public static void luanch(Context context, String vUrl) {
        Intent goTo = new Intent();
        goTo.setClass(context, VideoFetchingActivity.class);
        goTo.putExtra(KEY_VIDEO_URL, vUrl);
        context.startActivity(goTo);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_fetching);

        //view
        mSelectPreview = findViewById(R.id.iv_cover);
        mFramesSeekbar = findViewById(R.id.vfs_seekbar);

        findViewById(R.id.iv_close).setOnClickListener(v -> {
            this.finish();
        });
        findViewById(R.id.iv_confirm).setOnClickListener(v -> {
            confirm();
        });

        //data
        mVideoPath = getIntent().getStringExtra(KEY_VIDEO_URL);

        initView();
    }

    private void initView() {
        //seekbar
        mFramesSeekbar.bindVideoResource(mVideoPath);
        mFramesSeekbar.addOnProgressChangeListener(new VideoFetchingSeekbar.OnProgressChangeListener() {
            @Override
            public void onProgressChange(long progressMS) {
                Log.e(VideoFetchingActivity.class.getSimpleName(), "onProgressChange: " + progressMS);
            }

            @Override
            public void onObtainBitmapComp(Bitmap currentFrame) {
                mSelectPreview.setImageBitmap(currentFrame);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mHelper == null) {
            mHelper = VideoHelper.getInstance();
        }
        checkPermission();
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && this.checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            this.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);
            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "申请成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "拒绝权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void confirm() {
    }

}
