package com.example.wink_android;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;

public class OvalImageDrawable extends BitmapDrawable {

    public OvalImageDrawable(Bitmap bitmap) {
        super(bitmap);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        Bitmap bitmap = getBitmap();
        Rect bounds = getBounds();
        RectF rect = new RectF(bounds);

        // Scale the bitmap while maintaining aspect ratio
        Bitmap scaledBitmap = scaleBitmap(bitmap, bounds.width(), bounds.height());

        // Create a BitmapShader with the scaled bitmap and set as the paint shader
        BitmapShader shader = new BitmapShader(scaledBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Matrix matrix = new Matrix();
        matrix.setTranslate(rect.left, rect.top);
        shader.setLocalMatrix(matrix);
        paint.setShader(shader);

        // Draw the oval shape on the canvas with the painted image
        canvas.drawOval(rect, paint);
    }

    private Bitmap scaleBitmap(Bitmap bitmap, int targetWidth, int targetHeight) {
        float scale;
        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();

        if (originalWidth > originalHeight) {
            scale = (float) targetWidth / originalWidth;
        } else {
            scale = (float) targetHeight / originalHeight;
        }

        // Create a matrix and scale the bitmap accordingly
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        // Return the scaled bitmap
        return Bitmap.createBitmap(bitmap, 0, 0, originalWidth, originalHeight, matrix, true);
    }
}





