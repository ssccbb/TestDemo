package com.sung.testdemo.tapview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.text.DecimalFormat;

/**
 * Create by sung at 2018/10/22
 *
 * @Description: 控制器内触摸反馈的view
 */
public class TapView extends View {
    private TapTouchEvent event;

    private int maxX = 0;
    private int maxY = 0;
    private int lastX = 0;// 触点x位置
    private int lastY = 0;// 触点y位置
    private long lastT;// 触点时间
    private int tag_x;// 上一次记录x位置（只用于区分左右滑动）
    private boolean doubleClick = false;

    public TapView(Context context) {
        super(context);
    }

    public TapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void addTapTouchEvent(TapTouchEvent event) {
        this.event = event;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (maxX == 0) {
            maxX = this.getRight();
        }
        if (maxY == 0) {
            maxY = this.getBottom();
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                long currentT = System.currentTimeMillis();
                if ((currentT - lastT) <= 300) {
                    doubleClick = true;
                }
                lastT = currentT;
                if (this.event != null) {
                    this.event.recordPointXY("ACTION_DOWN", maxX, maxY, lastX, lastY, 0, 0, 0, 0, 0, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                int upX = (int) event.getRawX();
                int upY = (int) event.getRawY();
                if (this.event != null) {
                    this.event.onTouchEnd(Math.abs(upX - lastX) <= 12 && Math.abs(upY - lastY) <= 12, doubleClick);
                    doubleClick = false;
                }
                if (this.event != null) {
                    this.event.recordPointXY("ACTION_UP", maxX, maxY, lastX, lastY, upX, upY, 0, 0, 0, 0);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int currentX = (int) event.getRawX();
                int currentY = (int) event.getRawY();
                int moveX = currentX - lastX;
                int moveY = currentY - lastY;

                /*  屏幕最大x坐标的一半，即中间位置，小数左边大数右边  */
                boolean isLeftAreaTouch = currentX <= maxX / 2;
                /*  200表示手指在x轴方向上的偏移量，此处设大点但是不宜过大（个人感觉） */
                boolean isSlideUp = moveY < -10 && Math.abs(moveX) <= 200;

                float percentY = (float) Math.abs(moveY) / (float) Math.abs(maxY / 2);
                float percentX = (float) Math.abs(moveX) / (float) Math.abs(maxX / 2);
                DecimalFormat df = new DecimalFormat("#.0");
                percentX = Float.parseFloat(df.format(percentX));
                percentY = Float.parseFloat(df.format(percentY));

                if (this.event != null && Math.abs(moveY) > 15 && Math.abs(moveX) < 30){
                    this.event.onVerticalSlide(isLeftAreaTouch,isSlideUp,percentY);
                }
                if (this.event != null && Math.abs(moveY) < 30 && Math.abs(moveX) > 15){
                    this.event.onHorizontalSlide(currentX < tag_x ? false : true,percentX);
                    tag_x = currentX;
                }
                if (this.event != null) {
                    this.event.recordPointXY("ACTION_MOVE", maxX, maxY, lastX, lastY, currentX, currentY, moveX, moveY, percentX, percentY);
                }
                break;
        }
        return true;
    }
}
