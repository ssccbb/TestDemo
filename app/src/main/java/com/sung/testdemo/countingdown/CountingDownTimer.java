package com.sung.testdemo.countingdown;

import android.content.Context;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sung.testdemo.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Create by sung at 2019/3/27
 *
 * @Description: 倒计时
 */
public class CountingDownTimer extends Chronometer implements Chronometer.OnChronometerTickListener {
    private static final String FONT_DIGITAL_7 = "fonts" + File.separator + "digital-7.ttf";
    private static final String TAG = CountingDownTimer.class.getSimpleName();
    private static final String DATE_FORMAT = "%02d:%02d:%02d";

    private CountingDownListener listener;
    private long time = 0L;

    private boolean isCountingDown = false;

    public CountingDownTimer(Context context) {
        super(context);
    }

    public CountingDownTimer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CountingDownTimer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), FONT_DIGITAL_7);
        this.setTextColor(getContext().getResources().getColor(R.color.colorAccent));
        this.setFormat("HH:MM:SS");
        this.setTypeface(font);
        this.setOnChronometerTickListener(this);
    }

    @Override
    public void onChronometerTick(Chronometer chronometer) {
        isCountingDown = true;
        updateTimeText();
        time = time - 1000;
        if (time < 0) {
            this.stop();
            isCountingDown = false;
            if (listener != null) {
                listener.done();
            }
        }
    }

    private void updateTimeText() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        calendar.setTime(new Date(time));
        this.setText(String.format(DATE_FORMAT, calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND)));
        Log.e(TAG, "on tick ---> "+time );
    }

    /**
     * 重置
     */
    public void reset(int min) {
        stop();
        time = min * 60 * 1000;
        updateTimeText();
    }

    /**
     * 开始
     */
    @Override
    public void start() {
        if (time <= 0) {
            if (listener != null) {
                listener.done();
            }
            return;
        }
        this.setBase(SystemClock.elapsedRealtime());
        super.start();
    }

    /**
     * 暂停
     */
    public void pause() {
        time = time + 1000;
        this.stop();
    }

    @Override
    public void stop() {
        super.stop();
        isCountingDown = false;
    }

    /**
     * 停止
     */
    public void cancel() {
        time = 0;
        super.stop();
        updateTimeText();
    }

    /**
     * @return 是否在计时中
     */
    public boolean isCountingDown() {
        return isCountingDown;
    }

    public interface CountingDownListener {
        void done();
    }

    public void addCountingDownListener(CountingDownListener listener) {
        this.listener = listener;
    }
}
