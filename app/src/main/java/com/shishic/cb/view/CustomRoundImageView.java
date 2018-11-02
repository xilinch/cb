package com.shishic.cb.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.shishic.cb.R;


/**
 * 自定义圆角图片，能自定义上圆角和下圆角大小
 */

public class CustomRoundImageView extends ImageView {
    private float topRadius;
    private float bottomRadius;
    /*圆角的半径，依次为左上角xy半径，右上角，右下角，左下角*/
    private float[] p;
    private Path path;
    private RectF rect;

    public CustomRoundImageView(Context context) {
        this(context, null);
    }

    public CustomRoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomRoundImageView);
            topRadius = a.getDimension(R.styleable.CustomRoundImageView_topCornerRadius, 0.0f);
            bottomRadius = a.getDimension(R.styleable.CustomRoundImageView_bottomCornerRadius, 0.0f);
            a.recycle();
        }
        p = new float[]{topRadius, topRadius, topRadius, topRadius, bottomRadius, bottomRadius, bottomRadius, bottomRadius};
        path = new Path();
        rect = new RectF(0, 0, 0, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        rect.bottom = getMeasuredHeight();
        rect.right = getMeasuredWidth();
    }

    protected void onDraw(Canvas canvas) {
        path.addRoundRect(rect, p, Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }

}
