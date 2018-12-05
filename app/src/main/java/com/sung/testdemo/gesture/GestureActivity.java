package com.sung.testdemo.gesture;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

import com.sung.testdemo.R;

public class GestureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture);

        PlayerGestureBoard board = findViewById(R.id.tgb_board);
        board.setVideoGestureListener(new PlayerGestureBoard.VideoGestureListener() {
            @Override
            public void onBrightnessGesture(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            }

            @Override
            public void onVolumeGesture(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            }

            @Override
            public void onFF_REWGesture(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            }

            @Override
            public void onSingleTapGesture(MotionEvent e) {

            }

            @Override
            public void onDoubleTapGesture(MotionEvent e) {

            }

            @Override
            public void onDown(MotionEvent e) {

            }

            @Override
            public void onEndFF_REW(MotionEvent e) {

            }
        });
    }
}
