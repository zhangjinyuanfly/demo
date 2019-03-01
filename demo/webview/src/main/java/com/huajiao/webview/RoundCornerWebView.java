package com.huajiao.webview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * Created by fubiwei on 2017/12/24.
 * 圆角WebView
 */

public class RoundCornerWebView extends CommonWebView {

    private final Path mRoundCornerPath = new Path();
    private final RectF mRoundCornerRectF = new RectF();
    private final Paint mRoundCornerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    {
        mRoundCornerPaint.setColor(Color.TRANSPARENT);
        mRoundCornerPaint.setStyle(Paint.Style.FILL);
        mRoundCornerPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    private boolean mIsError = false;
    private final float[] mCornerRadius = new float[8];
    private float mLeftTopRadius;
    private float mRightTopRadius;
    private float mLeftBottomRadius;
    private float mRightBottomRadius;

    public RoundCornerWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        final TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.RoundCornerWebView);
        mLeftTopRadius = typedArray.getDimension(R.styleable.RoundCornerWebView_leftTopRadius, 0);
        mRightTopRadius = typedArray.getDimension(R.styleable.RoundCornerWebView_rightTopRadius, 0);
        mLeftBottomRadius = typedArray.getDimension(R.styleable.RoundCornerWebView_leftBottomRadius, 0);
        mRightBottomRadius = typedArray.getDimension(R.styleable.RoundCornerWebView_rightBottomRadius, 0);
        mCornerRadius[0] = mLeftTopRadius;
        mCornerRadius[1] = mLeftTopRadius;
        mCornerRadius[2] = mRightTopRadius;
        mCornerRadius[3] = mRightTopRadius;
        mCornerRadius[4] = mLeftBottomRadius;
        mCornerRadius[5] = mLeftBottomRadius;
        mCornerRadius[6] = mRightBottomRadius;
        mCornerRadius[7] = mRightBottomRadius;

        typedArray.recycle();
    }

    public RoundCornerWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundCornerWebView(Context context) {
        this(context, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mIsError) {
            return;
        }
        mRoundCornerPath.reset();
        mRoundCornerPath.setFillType(Path.FillType.INVERSE_WINDING);
        mRoundCornerRectF.setEmpty();
        mRoundCornerRectF.set(0, getScrollY(), getMeasuredWidth(), getMeasuredHeight() + getScrollY());
        mRoundCornerPath.addRoundRect(mRoundCornerRectF,
                mCornerRadius
                , Path.Direction.CW);
        canvas.drawPath(mRoundCornerPath, mRoundCornerPaint);
    }

    public void setLeftTopRadius(float leftTopRadius) {
        mLeftTopRadius = leftTopRadius;
        mCornerRadius[0] = mLeftTopRadius;
        mCornerRadius[1] = mLeftTopRadius;
    }

    public void setRightTopRadius(float rightTopRadius) {
        mRightTopRadius = rightTopRadius;
        mCornerRadius[2] = mRightTopRadius;
        mCornerRadius[3] = mRightTopRadius;
    }

    public void setLeftBottomRadius(float leftBottomRadius) {
        mLeftBottomRadius = leftBottomRadius;
        mCornerRadius[4] = mLeftBottomRadius;
        mCornerRadius[5] = mLeftBottomRadius;
    }

    public void setRightBottomRadius(float rightBottomRadius) {
        mRightBottomRadius = rightBottomRadius;
        mCornerRadius[6] = mRightBottomRadius;
        mCornerRadius[7] = mRightBottomRadius;
    }


    @Override
    public void onReceivedError() {
        mIsError = true;
        invalidate();
    }


}
