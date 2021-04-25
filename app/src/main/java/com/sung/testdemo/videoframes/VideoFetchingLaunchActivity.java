package com.sung.testdemo.videoframes;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.sung.testdemo.R;

import java.util.ArrayList;
import java.util.HashMap;

public class VideoFetchingLaunchActivity extends AppCompatActivity {
    private TextView mPathDisplay;
    private RecyclerView mVideoList;

    private String mPath = "";
    private ArrayList<HashMap<String, String>> mVideoMapList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_fetching_launch);

        //data
        mVideoMapList = new ArrayList<>();

        //view
        mPathDisplay = findViewById(R.id.tv_path);
        mVideoList = findViewById(R.id.rc_list);

        //click
        findViewById(R.id.btn_go).setOnClickListener(v -> {
            goTo();
        });

        checkPermission();
    }

    private void set(){
        //get videos
        boolean complete = getVideos();
        if (!complete) return;

        //recyclerview
        mVideoList.setLayoutManager(new LinearLayoutManager(this));
        mVideoList.setHasFixedSize(true);
        mVideoList.setItemAnimator(new DefaultItemAnimator());
        VideoListAdapter adapter = new VideoListAdapter(mVideoMapList);
        adapter.addOnItemClickListener(path -> {
            mPath = path;
            mPathDisplay.setText(mPath);
        });
        mVideoList.setAdapter(adapter);
    }

    private boolean getVideos() {
        Uri videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        ContentResolver cr = getContentResolver();
        String[] projection = new String[]{
                MediaStore.Video.VideoColumns.DATA, MediaStore.Video.VideoColumns.DURATION,
                MediaStore.Video.VideoColumns.DISPLAY_NAME, MediaStore.Video.VideoColumns.DATE_ADDED
        };

        Cursor cursor = cr.query(videoUri, projection, null, null, null);
        if (cursor == null) {
            return false;
        }
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                String name = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
                String date = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATE_ADDED));
                long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));

                HashMap<String, String> map = new HashMap<>();
                map.put("key_path", path);
                map.put("key_name", name);
                map.put("key_date", date);
                map.put("key_duration", String.valueOf(duration));
                mVideoMapList.add(map);
            }
        }
        cursor.close();
        return true;
    }

    private void goTo() {
        String path = mPathDisplay.getText().toString().trim();
        if (path == null || path.length() == 0) {
            Toast.makeText(this, "path error!", Toast.LENGTH_SHORT).show();
            return;
        }
        VideoFetchingActivity.luanch(VideoFetchingLaunchActivity.this, path);
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && this.checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            this.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);
            return;
        } else {
            set();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "申请成功", Toast.LENGTH_SHORT).show();
                    set();
                } else {
                    Toast.makeText(this, "拒绝权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
