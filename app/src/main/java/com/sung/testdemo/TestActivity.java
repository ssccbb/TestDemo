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
import com.sung.testdemo.scoreboard.ScoreBoardActivity;
import com.sung.testdemo.tapview.TapActivity;
import com.sung.testdemo.text2pic.Text2PicActivity;
import com.sung.testdemo.viewtobitmap.SnapshotActivity;
import com.sung.testdemo.zoomscrollview.PullerActivity;
import com.sung.testdemo.zoomscrollview.ZoomActivity;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {
    private RecyclerView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        final List<String> data = new ArrayList();
        data.add("生命周期");
        data.add("视频播放控制");
        data.add("文字生成图片");
        data.add("手势操控");
        data.add("比赛记分牌图片生成");
        data.add("GRID间隔");
        data.add("LiveData&ViewModel");
        data.add("横竖屏切换bug");
        data.add("头部放大scrollview");
        data.add("头尾可拉动scrollview");
        data.add("尾部隐藏内容的viewgroup");
        data.add("Metiral Design 控件");
        data.add("表格记录");
        data.add("倒计时");
        data.add("自适应数量表格布局");
        data.add("view截图生成图片");

        list = findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setHasFixedSize(true);
        list.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false));
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
                Holder holder1 = (Holder) holder;
                holder1.text.setText(data.get(position));
                holder1.text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position == 0){
                            startActivity(new Intent(TestActivity.this,Main1Activity.class));
                        }
                        if (position == 1){
                            startActivity(new Intent(TestActivity.this,TapActivity.class));
                        }
                        if (position == 2){
                            startActivity(new Intent(TestActivity.this,Text2PicActivity.class));
                        }
                        if (position == 3){
                            startActivity(new Intent(TestActivity.this,GestureActivity.class));
                        }
                        if (position == 4){
                            startActivity(new Intent(TestActivity.this,ScoreBoardActivity.class));
                        }
                        if (position == 5){
                            startActivity(new Intent(TestActivity.this,GridActivity.class));
                        }
                        if (position == 6){
                            startActivity(new Intent(TestActivity.this,LiveData_ViewModelActivity.class));
                        }
                        if (position == 7){
                            startActivity(new Intent(TestActivity.this,OrientationActivity.class));
                        }
                        if (position == 8){
                            startActivity(new Intent(TestActivity.this,ZoomActivity.class));
                        }
                        if (position == 9){
                            startActivity(new Intent(TestActivity.this,PullerActivity.class));
                        }
                        if (position == 10){
                            startActivity(new Intent(TestActivity.this,HideLayoutActivity.class));
                        }
                        if (position == 11){
                            startActivity(new Intent(TestActivity.this,MetiralListActivity.class));
                        }
                        if (position == 12){
                            startActivity(new Intent(TestActivity.this, GridHistoryActivity.class));
                        }
                        if (position == 13){
                            startActivity(new Intent(TestActivity.this, CountingDownActivity.class));
                        }
                        if (position == 14){
                            startActivity(new Intent(TestActivity.this, AutoColumnGridActivity.class));
                        }
                        if (position == 15){
                            startActivity(new Intent(TestActivity.this, SnapshotActivity.class));
                        }
                    }
                });
            }

            @Override
            public int getItemCount() {
                return data.size();
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
