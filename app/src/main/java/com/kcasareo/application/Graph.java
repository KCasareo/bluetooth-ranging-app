package com.kcasareo.application;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Kevin on 28/09/2017.
 */

public class Graph extends View {
    private Bitmap plane = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
    private Canvas canvas = new Canvas(plane);
    private Paint mGraphPaint;

    public Graph(Context context) {
        super(context);
    }

    public Graph(Context context, AttributeSet attrs) {
        super(context, attrs);
        /*
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.HistoryAdapter,
                0, 0;
        );*/
    }

    private void init() {
        mGraphPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mGraphPaint.setStyle(Paint.Style.FILL);

    }

    @Override
    public void onDraw(Canvas canvas) {
        //super.onDraw(canvas);

    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {

    }



}
