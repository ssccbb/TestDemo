package com.sung.testdemo.gesture;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.sung.testdemo.R;

/**
 * Create by sung at 2018/11/12
 *
 * @Description: 播放界面ui
 */
public class PlayerDisplayUILayout extends FrameLayout {

    public PlayerDisplayUILayout(@NonNull Context context) {
        super(context);
        init();
    }

    public PlayerDisplayUILayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        View root = inflater.inflate(R.layout.layout_player_display_ui,this,false);
        this.addView(root,new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
    }
}
