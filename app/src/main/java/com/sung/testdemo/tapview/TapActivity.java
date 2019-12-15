package com.sung.testdemo.tapview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sung.testdemo.R;

public class TapActivity extends AppCompatActivity {
    private TapView tapView;
    private TextView text;
    private View dialog;
    private ProgressBar progress;
    private ProgressBar playback;
    private ImageView status;
    private int last_move_x = 0;

    private int old_progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tap);

        tapView = findViewById(R.id.tap_view);
        text = findViewById(R.id.log);
        dialog = findViewById(R.id.v_dialog);
        progress = findViewById(R.id.light_progress);
        playback = findViewById(R.id.pb_progress);
        status = findViewById(R.id.iv_status);

        progress.setMax(10);
        progress.setProgress(old_progress);
        dialog.setVisibility(View.GONE);
        tapView.addTapTouchEvent(new TapTouchEvent() {
            @Override
            public void onTouchEnd(boolean isClick, boolean isDoubleClick) {
                old_progress = progress.getProgress();
                dialog.setVisibility(View.GONE);
                status.setVisibility(View.GONE);
                if (isClick && isDoubleClick) {
                    Toast.makeText(TapActivity.this, "双击", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onVerticalSlide(boolean isLeft, boolean slideUp, float percent) {
                dialog.setVisibility(View.VISIBLE);
                int change = (int) (percent * 10);
                if (slideUp) {
                    int current = old_progress + change;
                    if (current > 10) current = 10;
                    progress.setProgress(current);
                } else {
                    int current = old_progress - change;
                    if (current < 0) current = 0;
                    progress.setProgress(current);
                }
            }

            @Override
            public void onHorizontalSlide(boolean left2right, float percent) {
                Log.e(TapActivity.class.getSimpleName(), "onHorizontalSlide: " + left2right + "/" + percent);
                int progress = 50 + (int) (left2right ? percent * 10 : (0 - percent * 10));
                playback.setProgress(progress);

                status.setVisibility(View.VISIBLE);
                status.setImageResource(left2right ? R.mipmap.fast_forward : R.mipmap.fast_back);
            }

            @Override
            public void recordPointXY(String tag, int maxX, int maxY, int lastX, int lastY, int currentX, int currentY, int moveX, int moveY, float percentX, float percentY) {
                String str = buildLogs(tag, maxX, maxY, lastX, lastY, currentX, currentY, moveX, moveY, percentX, percentY);
                text.setText(str);
            }
        });
    }

    private String buildLogs(String tag, int maxX, int maxY, int lastX, int lastY, int currentX, int currentY, int moveX, int moveY, float percentX, float percentY) {
        StringBuffer s = new StringBuffer();
        s.append("tag --> " + tag);
        s.append("\nmax --> x:" + maxX + ",y:" + maxY);
        s.append("\nlast (x,y) --> " + "(" + lastX + "," + lastY + ")");
        s.append("\ncurrent (x,y) --> " + "(" + currentX + "," + currentY + ")");
        s.append("\nmove --> x:" + moveX + " y:" + moveY);
        boolean isSlideUp = moveY < -10 && Math.abs(moveX) <= 100;
        boolean isSledeLeft = currentX > last_move_x ? false : true;
        last_move_x = currentX;
        s.append("\nmove --> x:" + (isSledeLeft ? "左滑" : "右滑") + " y:" + (isSlideUp ? "上滑" : "下滑"));
        s.append("\npercentX --> moveX/maxX = " + percentX);
        s.append("\npercentY --> moveY/maxY = " + percentY);
        return s.toString();
    }
}
