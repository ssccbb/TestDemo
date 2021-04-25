package com.sung.testdemo.autoscrollview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.sung.testdemo.R;

/**
 * @author by sung at 4/25/21
 * @version clazz AutoScroollView 1th edition
 * @desc 类似小红书长背景图垂直循环滚动
 *       使用ScrollView是为了更好的适配UI，根据传入图片的宽高比
 *       自动设置内嵌ImageView的宽高，借助位移动画去执行长图滚动
 */
public class AutoScrollBackgroundView extends ScrollView implements Animation.AnimationListener {
    private static final int DEFAULT_DURATION = 6 * 1000;
    private static final int DEFAULT_REPEATCOUNT = 1000;

    private int imgRes;
    private int animDuration;
    private int animRepeatCount;
    private boolean animExecuting = false;

    private TranslateAnimation translateAnimation = null;

    private Handler animHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            go();
        }
    };

    public AutoScrollBackgroundView(Context context) {
        this(context, null);
    }

    public AutoScrollBackgroundView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoScrollBackgroundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AutoScrollBackgroundView, defStyleAttr, 0);
        try {
            imgRes = typedArray.getResourceId(R.styleable.AutoScrollBackgroundView_android_src, -1);
            animDuration = typedArray.getInteger(R.styleable.AutoScrollBackgroundView_duration, DEFAULT_DURATION);
            animRepeatCount = typedArray.getInteger(R.styleable.AutoScrollBackgroundView_repeatCount, DEFAULT_REPEATCOUNT);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // 防止xml内写入子控件
        removeAllViews();
        // 添加默认控件以及初始化
        addDefaultView();
        // 去除滚动指示条
        setVerticalScrollBarEnabled(false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // 屏蔽触摸事件
        return true;
    }

    private void addDefaultView() {
        LinearLayout container = new LinearLayout(getContext());
        // 长背景图ImageView
        ImageView background = new ImageView(getContext());
        // 拿到宽高再进行初始化
        background.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Bitmap imgage = null;
            try {
                // decode图片资源
                imgage = BitmapFactory.decodeResource(getResources(), imgRes);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (imgage == null) {
                // 背景图设置失败自动隐藏
                AutoScrollBackgroundView.this.setVisibility(GONE);
                return;
            }
            // 图片高度
            float imgHeight = imgage.getHeight();
            // 图片宽度
            float imgWidth = imgage.getWidth();
            if (imgHeight == 0 || imgWidth == 0) {
                // 背景图设置失败自动隐藏
                AutoScrollBackgroundView.this.setVisibility(GONE);
                return;
            }
            // 图片比例
            float imgScale = imgHeight / imgWidth;
            // 长背景图ImageView宽度（充满）
            int viewWidth = background.getWidth();
            // 长背景图ImageView高度（保持与图片同比例）
            int viewHeight = (int) (viewWidth * imgScale);
            // 初始化设置
            background.getLayoutParams().height = viewHeight;
            background.setImageBitmap(imgage);
            background.setScaleType(ImageView.ScaleType.FIT_XY);

            // 背景图片初始化完成
            animHandler.sendEmptyMessageDelayed(0,300);
        });
        this.addView(container, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        container.addView(background, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void go() {
        if (animExecuting) {
            // 动画执行中拦截
            Log.d(AutoScrollBackgroundView.class.getSimpleName(), "action go: Failed!Animation executing...");
            return;
        }
        int height = getMeasuredHeight();
        if (height <= 0 || getChildCount() == 0) {
            return;
        }
        View child = getChildAt(0);
        // 位移动画
        if (translateAnimation == null) {
            translateAnimation = new TranslateAnimation(0, 0, 0, -(child.getMeasuredHeight() - height));
            translateAnimation.setDuration(animDuration);
            translateAnimation.setRepeatMode(Animation.REVERSE);
            translateAnimation.setRepeatCount(animRepeatCount);
            translateAnimation.setInterpolator(new LinearInterpolator());
            translateAnimation.setAnimationListener(this);
        }
        child.startAnimation(translateAnimation);
    }

    public void go(int duration) {
        int height = getMeasuredHeight();
        if (height <= 0 || getChildCount() == 0) {
            return;
        }
        View child = getChildAt(0);
        child.clearAnimation();
        // 位移动画
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, -(child.getMeasuredHeight() - height));
        translateAnimation.setDuration(duration);
        translateAnimation.setRepeatMode(Animation.REVERSE);
        translateAnimation.setRepeatCount(animRepeatCount);
        translateAnimation.setInterpolator(new LinearInterpolator());
        translateAnimation.setAnimationListener(this);
        child.startAnimation(translateAnimation);
    }

    @Override
    public void onAnimationStart(Animation animation) {
        animExecuting = true;
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        animExecuting = false;
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
