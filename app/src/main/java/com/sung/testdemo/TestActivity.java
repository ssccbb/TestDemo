package com.sung.testdemo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sung.testdemo.GridScoreBoard.GridHistoryActivity;
import com.sung.testdemo.autocolumngridview.AutoColumnGridActivity;
import com.sung.testdemo.countingdown.CountingDownActivity;
import com.sung.testdemo.gesture.GestureActivity;
import com.sung.testdemo.griddecoration.GridActivity;
import com.sung.testdemo.hidelayout.HideLayoutActivity;
import com.sung.testdemo.livedata_viewmodel.LiveData_ViewModelActivity;
import com.sung.testdemo.main.Main1Activity;
import com.sung.testdemo.metiraldesign.MetiralListActivity;
import com.sung.testdemo.orientation.OrientationActivity;
import com.sung.testdemo.randomnickname.RandomNicknameActivity;
import com.sung.testdemo.scoreboard.ScoreBoardActivity;
import com.sung.testdemo.tapview.TapActivity;
import com.sung.testdemo.text2pic.Text2PicActivity;
import com.sung.testdemo.videoframes.VideoFetchingActivity;
import com.sung.testdemo.videoframes.VideoFetchingLaunchActivity;
import com.sung.testdemo.viewtobitmap.SnapshotActivity;
import com.sung.testdemo.zoomscrollview.PullerActivity;
import com.sung.testdemo.zoomscrollview.ZoomActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sung
 */
public class TestActivity extends AppCompatActivity {
    private RecyclerView list;

    private String[] titles = {
            "生命周期",
            "视频播放控制",
            "文字生成图片",
            "手势操控",
            "比赛记分牌图片生成",
            "GRID间隔",
            "LiveData&ViewModel",
            "横竖屏切换bug",
            "头部放大scrollview",
            "头尾可拉动scrollview",
            "尾部隐藏内容的viewgroup",
            "Metiral Design 控件",
            "表格记录",
            "倒计时",
            "自适应数量表格布局",
            "view截图生成图片",
            "生成随机昵称",
            "获取视频封面截图"
    };
    private Class[] classes = {
            Main1Activity.class,
            TapActivity.class,
            Text2PicActivity.class,
            GestureActivity.class,
            ScoreBoardActivity.class,
            GridActivity.class,
            LiveData_ViewModelActivity.class,
            OrientationActivity.class,
            ZoomActivity.class,
            PullerActivity.class,
            HideLayoutActivity.class,
            MetiralListActivity.class,
            GridHistoryActivity.class,
            CountingDownActivity.class,
            AutoColumnGridActivity.class,
            SnapshotActivity.class,
            RandomNicknameActivity.class,
            VideoFetchingLaunchActivity.class
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        list = findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setHasFixedSize(true);
        list.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
                Holder holder1 = (Holder) holder;
                holder1.text.setText(titles[position]);
                holder1.text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(TestActivity.this, classes[position]));
                    }
                });
            }

            @Override
            public int getItemCount() {
                return titles.length;
            }
        });
    }

    class Holder extends RecyclerView.ViewHolder {
        public TextView text;

        public Holder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
        }
    }
}
