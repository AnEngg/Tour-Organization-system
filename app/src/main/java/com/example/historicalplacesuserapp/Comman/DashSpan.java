package com.example.historicalplacesuserapp.Comman;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.ReplacementSpan;

import androidx.annotation.NonNull;

public class DashSpan extends ReplacementSpan {

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        float padding = paint.measureText("-", 0, 1);
        float textSize = paint.measureText(text, start, end);
        return (int) (padding + textSize);
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y,
                     int bottom, @NonNull Paint paint) {
        canvas.drawText(text.subSequence(start, end) + "-", x, y, paint);
    }
}
