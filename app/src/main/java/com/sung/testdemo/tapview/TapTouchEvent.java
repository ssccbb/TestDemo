package com.sung.testdemo.tapview;

/**
 * Create by sung at 2018/10/22
 *
 * @Description:
 */
public interface TapTouchEvent {
    void onTouchEnd(boolean isClick, boolean isDoubleClick);
    void onVerticalSlide(boolean isLeft, boolean slideUp, float percent);
    void onHorizontalSlide(boolean right2left, float percent);
    void recordPointXY(String tag,int maxX,int maxY,int lastX,int lastY,int currentX,int currentY,int moveX,int moveY,float percentX,float percentY);
}
