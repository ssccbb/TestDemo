package com.sung.testdemo.videoframes;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
    private static final String KEY_FRAME_NUMBER = "key_frame_num";
    private static final String KEY_VIDEO_URL = "key_video_url";

    private int mDefaultFrameNumber = 5;
    private List<String> mFramePicPath;
    private VideoFrameAdapter mFramesAdapter;

    private ImageView mSelectPreview;
    private RecyclerView mFramesPreview;

    private VideoHelper mHelper;

    public static void luanch(Context context, String vUrl, int keyFrame) {
        Intent goTo = new Intent();
        goTo.setClass(context, VideoFetchingActivity.class);
        goTo.putExtra(KEY_FRAME_NUMBER, keyFrame < 3 ? 5 : keyFrame);
        goTo.putExtra(KEY_VIDEO_URL, vUrl);
        context.startActivity(goTo);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_fetching);

        mSelectPreview = findViewById(R.id.iv_cover);
        mFramesPreview = findViewById(R.id.rc_frames);

        findViewById(R.id.iv_close).setOnClickListener(v -> {
            this.finish();
        });
        findViewById(R.id.iv_confirm).setOnClickListener(v -> {
            confirm();
        });

        initView();
    }

    private void initView() {
        if (mFramePicPath == null) mFramePicPath = new ArrayList<>();
        mFramesPreview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mFramesPreview.setHasFixedSize(true);
        mFramesPreview.setItemAnimator(new DefaultItemAnimator());
        mFramesAdapter = new VideoFrameAdapter();
        mFramesPreview.setAdapter(mFramesAdapter);
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
        getVideoFrams();
    }

    private void getVideoFrams() {
        mHelper.getVideoThumbInBackstage("http://fdfs-uat.tyi365.com/video/M00/00/01/rB_IzV1H9yCAYhS5ACjmhK8C2Lc857.mp4", 5, new VideoHelper.callback<List<String>>() {
            @Override
            public void complete(List<String> strings) {
                try {
                    if (mFramePicPath == null) mFramePicPath = new ArrayList<>();
                    mFramePicPath.clear();
                    mFramePicPath.addAll(strings);
                    if (mFramesAdapter != null) mFramesAdapter.notifyDataSetChanged();
                    for (String string : strings) {
                        Log.e(VideoFetchingActivity.class.getSimpleName(), "complete: " + string);
                    }
                } catch (Exception e) {
                    Log.e(VideoFetchingActivity.class.getSimpleName(), "complete: " + e.toString());
                }
            }
        });
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

    public class VideoFrameAdapter extends RecyclerView.Adapter {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_video_frame_list_item, parent, false);
            return new VideoFrameHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((VideoFrameHolder) holder).onBind(position);
        }

        @Override
        public int getItemCount() {
            return mFramePicPath.size();
        }
    }

    public class VideoFrameHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mPreview;
        private ImageView mSelect;

        public VideoFrameHolder(View itemView) {
            super(itemView);
            mPreview = itemView.findViewById(R.id.iv_preview);
            mSelect = itemView.findViewById(R.id.iv_select);
        }

        void onBind(int position) {
            onLayoutChange();
            mPreview.setImageDrawable(getDrawableByPath(position));
            itemView.setOnClickListener(this::onClick);
            itemView.setTag(position);
        }

        @Override
        public void onClick(View v) {
            if (!(v.getTag() instanceof Integer)) return;
            mSelectPreview.setImageDrawable(getDrawableByPath((int) v.getTag()));
        }

        private void onLayoutChange() {
            ViewGroup.LayoutParams params = itemView.getLayoutParams();
            params.width = getScreenParms()[0] / 5;
        }

        private Drawable getDrawableByPath(int position) {
            String path = mFramePicPath.get(position);
            return new BitmapDrawable(path);
        }
    }

    private int[] getScreenParms() {
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        return new int[]{width, height};
    }
}
