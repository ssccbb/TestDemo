package com.sung.testdemo.hidelayout;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * Create by sung at 2018/12/5
 *
 * @Description:
 */
public class HideFooterConstraintLayout extends ConstraintLayout {
    private float lastPosition;//上次位置
    private float move;//移动距离

    private ValueAnimator anim;//动画

    private View inner;
    private View foot;
    private Rect normal;//用于存储开始滑动时的位置

    public HideFooterConstraintLayout(Context context) {
        super(context);
    }

    public HideFooterConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HideFooterConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        inner = getChildAt(0);
        foot = getChildAt(1);
        normal = new Rect();

    }
}
