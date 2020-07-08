package com.android.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.squareup.picasso.Transformation;
public class RoundCornersTransform implements Transformation {

    int radius;
    public RoundCornersTransform(int r) {
        radius= r;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        Bitmap bitmap = Bitmap.createBitmap(source.getWidth(), source.getHeight(), source.getConfig());
        Canvas canvas = new Canvas(bitmap);
        Paint paint =  new Paint(Paint.DITHER_FLAG);
        BitmapShader shader =  new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);;
        RectF rect = new RectF(0.0f, 0.0f, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rect, radius, radius, paint);
        source.recycle();
        return bitmap;
    }

    @Override
    public String key() {
        return "round_corners";
    }
}
