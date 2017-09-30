package com.kcasareo.application;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Kevin on 28/09/2017.
 */

public class Graph extends View {
    private Bitmap plane = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
    private Canvas canvas = new Canvas(plane);

    public Graph(Context context) {
        super(context);
    }

    public Graph(Context context, AttributeSet attrs) {
        super(context, attrs);
    }




}
