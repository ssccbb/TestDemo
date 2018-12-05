package com.sung.testdemo.scoreboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.sung.testdemo.R;

public class ScoreBoardActivity extends AppCompatActivity {
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);

        img = findViewById(R.id.img);

        int height = dip2px(this, 50);
        Bitmap bitmap = loadBitmapFromView(height * 5, height);
        img.setImageBitmap(bitmap);
    }

    /**
     * 通过view生成图片
     *
     * @param width  view显示宽度
     * @param height view显示高度
     * @Description 可应用于需要截图view内容
     */
    private Bitmap loadBitmapFromView(int width, int height) {
        View v = LayoutInflater.from(this).inflate(R.layout.view_score_board, null, false);
        v.layout(0, 0, width, height);
        int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.AT_MOST);

        // 当measure完后，并不会实际改变View的尺寸，需要调用View.layout方法去进行布局。
        v.measure(measuredWidth, measuredHeight);
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());

        int w = v.getWidth();
        int h = v.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);

        // 如果不设置canvas画布为白色，则透明背景
        //c.drawColor(Color.WHITE);

        v.layout(0, 0, w, h);
        v.draw(c);

        return bmp;
    }

    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
