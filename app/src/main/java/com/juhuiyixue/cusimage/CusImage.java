package com.juhuiyixue.cusimage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2016/5/17.
 */
public class CusImage extends View {

    private ButtonLayout b;
    private Paint myPaint;
    private float startAngle, sweepAngle;
    private RectF rect;
    // 默认控件大小
    private int pix = 160;

    public CusImage(Context context, ButtonLayout b) {
        super(context);
        this.b = b;
        init();
        // TODO Auto-generated constructor stub
    }

    public CusImage(Context context, AttributeSet attrs, ButtonLayout b) {
        super(context, attrs);
        this.b = b;
        init();
        // TODO Auto-generated constructor stub
    }

    private void init() {
        myPaint = new Paint();
        DisplayMetrics metrics = getContext().getResources()
                .getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        Log.d("TAG", width + "");
        Log.d("TAG", height + "");
        float scarea = width * height;
        pix = (int) Math.sqrt(scarea * 0.0217);

        //抗锯齿
        myPaint.setAntiAlias(true);
        //stroke表示空心，Fill表示实心
        myPaint.setStyle(Paint.Style.STROKE);
        //颜色
        myPaint.setColor(Color.rgb(0, 161, 234));
        //设置线条粗细
        myPaint.setStrokeWidth(7);

        float startx = (float) (pix * 0.05);
        float endx = (float) (pix * 0.95);
        float starty = (float) (pix * 0.05);
        float endy = (float) (pix * 0.95);
        //矩形区域
        rect = new RectF(startx, starty, endx, endy);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 画弧线
        // 在rect这个区域内画，开始的角度，扫过的度数而不是结束的角度,false表示不与圆心连线，true通常用来画扇形，画笔。
        canvas.drawArc(rect, startAngle, sweepAngle, false, myPaint);
        startAngle = -90;

        //小于1圈
        if (sweepAngle < 360 &&b.flg_frmwrk_mode == 2) {
            invalidate();
        }else if(b.flg_frmwrk_mode == 1){

        }else {//扫完一圈，调用b.finalAnimation()
            sweepAngle = 0;
            startAngle = -90;
            b.finalAnimation();

        }
        super.onDraw(canvas);
    }

    /**
     * 控制控件的大小 http://blog.csdn.net/pi9nc/article/details/18764863
     **/
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = pix;
        int desiredHeight = pix;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        // 如果控件宽度是指定大小，宽度为指定的尺寸
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) { // 没有限制，默认内容大小
            width = Math.min(desiredWidth, widthSize);
        } else {
            width = desiredWidth;
        }

        // 如果控件高度是指定大小，高度为指定的尺寸
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == View.MeasureSpec.AT_MOST) {// 没有限制，默认内容大小
            height = Math.min(desiredHeight, heightSize);
        } else {
            height = desiredHeight;
        }
        // 设定控件大小
        setMeasuredDimension(width, height);
    }
    // 传入参数
    public void setupprogress(int progress) {
        sweepAngle = (float) (progress * 3.6);
    }

    public void reset() {
        startAngle = -90;
    }

}