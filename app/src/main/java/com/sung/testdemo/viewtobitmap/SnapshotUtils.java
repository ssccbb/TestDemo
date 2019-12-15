package com.sung.testdemo.viewtobitmap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.view.View;

/**
 * Create by sung at 2019-12-05
 *
 * @Description:
 */
public class SnapshotUtils {
    /**
     * 计算view的大小
     */
    private static void measureSize(View viewBitmap) {
        viewBitmap.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        int height = viewBitmap.getMeasuredHeight();
        int width = viewBitmap.getMeasuredWidth();

        //然后View和其内部的子View都具有了实际大小，也就是完成了布局，相当与添加到了界面上。接着就可以创建位图并在上面绘制了：
        layoutView(viewBitmap, width, height);
    }

    /**
     * 填充布局内容
     */
    private static void layoutView(final View viewBitmap, int width, int height) {
        viewBitmap.layout(0, 0, width, height);
        int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.AT_MOST);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.AT_MOST);
        viewBitmap.measure(measuredWidth, measuredHeight);
        viewBitmap.layout(0, 0, viewBitmap.getMeasuredWidth(), viewBitmap.getMeasuredHeight());
    }

    /**
     * view转bitmap
     */
    private static Bitmap viewConversionBitmap(View v, boolean transparent, @ColorInt int color) {
        try {
            int w = v.getWidth();
            int h = v.getHeight();

            Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bmp);

            if (!transparent) {
                /** 如果不设置canvas画布为白色，则生成透明 */
                canvas.drawColor(color);
            }

            v.layout(0, 0, w, h);
            v.draw(canvas);
            return bmp;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 生成圆角图片
     */
    private static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
        try {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());
            final RectF rectF = new RectF(new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight()));
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(Color.BLACK);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            final Rect src = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());
            canvas.drawBitmap(bitmap, src, rect, paint);
            return output;
        } catch (Exception e) {
            return bitmap;
        }
    }

    public static Bitmap viewSnapshot(View view) {
        measureSize(view);

        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        view.setDrawingCacheBackgroundColor(Color.WHITE);

        Bitmap cachebmp = viewConversionBitmap(view, true, 0);
        view.destroyDrawingCache();

        return cachebmp;
    }

    public static Bitmap viewSnapshot(View view, @ColorInt int backgroundColor, float roundPx) {
        measureSize(view);

        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        view.setDrawingCacheBackgroundColor(Color.WHITE);

        Bitmap cachebmp = viewConversionBitmap(view, false, backgroundColor);
        view.destroyDrawingCache();

        return getRoundedCornerBitmap(cachebmp,roundPx);
    }
}
