package com.sung.testdemo.text2pic;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.sung.testdemo.R;

public class Text2PicActivity extends AppCompatActivity {
    private EditText content_text;
    private ImageView content_pic;
    private Button go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text2_pic);
        content_pic = findViewById(R.id.iv_content);
        content_text = findViewById(R.id.et_content);
        go = findViewById(R.id.btn_go);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap b = creatBitmapByText(content_text.getText().toString().trim());
                if (b != null) content_pic.setImageBitmap(b);
            }
        });
    }

    private Bitmap creatBitmapByText(String content) {
        if (content == null || content.length() == 0) {
            Toast.makeText(this, "文字为空", Toast.LENGTH_SHORT).show();
            return null;
        }
        try {
            int width = 500;
            int height = 450;
            Bitmap newb = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(newb);
            TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);
            int texsize = 60;
            textPaint.setTextSize(texsize);
            textPaint.setTypeface(Typeface.DEFAULT_BOLD);
            textPaint.setColor(Color.RED);
            textPaint.setDither(true);
            textPaint.setFilterBitmap(true);
            StaticLayout layout = new StaticLayout(content, textPaint, width,
                    Layout.Alignment.ALIGN_NORMAL, 1, 0, true);
            canvas.translate(0, 100);
            layout.draw(canvas);
            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();
            return newb;
        } catch (Exception e) {
            Toast.makeText(this, "图片生成错误", Toast.LENGTH_SHORT).show();
        }
        return null;
    }
}
