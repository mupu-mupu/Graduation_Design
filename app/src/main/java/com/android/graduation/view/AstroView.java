package com.android.graduation.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.android.graduation.R;

import java.util.Calendar;

/**
 * Created by asus on 2017/4/7.
 */

public class AstroView extends View {

    private Paint whitePaint;
    private Paint colorPaint;
    private Paint textPaint;
    private Path mArcPath;

    private float mWidth;
    private float mHeight;
    private static int PADDING = 40;

    private String sr;
    private String ss;
    private float angle;


    public AstroView(Context context) {
        super(context);
        init();
    }

    public AstroView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AstroView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        colorPaint = new Paint();
        colorPaint.setAntiAlias(true);
        colorPaint.setStyle(Paint.Style.STROKE);
        colorPaint.setColor(Color.YELLOW);
        colorPaint.setStrokeWidth(2);

        PathEffect effects = new DashPathEffect(new float[]{5,5,5,5},2);
        colorPaint.setPathEffect(effects);

        whitePaint = new Paint();
        whitePaint.setAntiAlias(true);
        whitePaint.setStyle(Paint.Style.STROKE);
        whitePaint.setColor(Color.WHITE);
        whitePaint.setStrokeWidth(2);

        textPaint = new TextPaint();
        textPaint.setColor(Color.WHITE);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(20);

        mArcPath = new Path();

        sr = "06:20";
        ss = "18:20";
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        mHeight = getHeight();
        float centerX = mWidth /2;
        float centerY = mHeight - PADDING;

        calculateCoordin();

        Shader shader = new SweepGradient(centerX,centerY, new int[] { Color.WHITE,Color.YELLOW,}, null);
        Matrix matrix = new Matrix();
        // 使用matrix改变渐变色起始位置，默认是在90度位置
        matrix.setRotate(-(180 - angle), centerX, centerY);
        shader.setLocalMatrix(matrix);
        colorPaint.setShader(shader);

        invalidate();
    }

    /*
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        mWidth = getWidth();
        mHeight = getHeight();
        mArcPath.moveTo(2 * PADDING,mHeight - PADDING);
        float centerX = mWidth /2;
        float centerY = mHeight - PADDING;

        calculateCoordin();

        *//* 设置渐变色 *//*
        Shader shader = new SweepGradient(centerX,centerY, new int[] { Color.WHITE,Color.YELLOW,}, null);
        Matrix matrix = new Matrix();
        // 使用matrix改变渐变色起始位置，默认是在90度位置
        matrix.setRotate(-(180 - angle), centerX, centerY);
        shader.setLocalMatrix(matrix);
        colorPaint.setShader(shader);

        super.onWindowFocusChanged(hasWindowFocus);
    }*/

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLine(canvas);
        drawArc(canvas);
        drawSun(canvas);
        drawAstroText(canvas);
    }

    private void drawAstroText(Canvas canvas) {
        Rect mTextRect = new Rect();
        String text = "18:20";
        textPaint.getTextBounds(text, 0, text.length(), mTextRect);
        int valueWidth = mTextRect.width();
        int valueHeight = mTextRect.height();
        canvas.drawText(sr,2 * PADDING - valueWidth / 2, mHeight - PADDING + 1.5f * valueHeight,textPaint);
        canvas.drawText(ss,(mWidth - 2 * PADDING) - valueWidth / 2, mHeight - PADDING + 1.5f * valueHeight,textPaint);
    }

    private void drawSun(Canvas canvas) {

        float t = angle / 180;
        float sunX = (1 - t) * (1 - t) * (2 * PADDING)  + 2 * t * (1 - t) * (mWidth / 2) + t * t * (mWidth - 2 * PADDING);
        float sunY = (1 - t) * (1 - t) * (mHeight - PADDING) + 2 * t * (1 - t) * (- mHeight / 5) + t * t * (mHeight - PADDING);

        BitmapDrawable sun = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_sun);
        float sunWidth = sun.getBitmap().getWidth()  / 1.5f;
        float sunHeight = sun.getBitmap().getHeight()/ 1.5f;
        float left = sunX  - sunWidth / 2;
        float top  = sunY - sunHeight / 2 ;
        float right = left + sunWidth;
        float bottom = top + sunHeight ;
        sun.setBounds((int)left,(int)top,(int)right,(int)bottom);
        sun.draw(canvas);
    }

    private void drawArc(Canvas canvas) {
        mArcPath.moveTo(2 * PADDING,mHeight - PADDING);
        mArcPath.quadTo(mWidth / 2,- mHeight / 5,mWidth - 2 * PADDING,mHeight - PADDING);
        canvas.drawPath(mArcPath,colorPaint);
    }

    private void drawLine(Canvas canvas) {
        canvas.drawLine(PADDING,mHeight -  PADDING,mWidth - PADDING,mHeight - PADDING,whitePaint);
    }

    private void calculateCoordin(){
        Calendar calendar = Calendar.getInstance();
        float minutes = calendar.get(Calendar.MINUTE);
        float hour  = calendar.get(Calendar.HOUR_OF_DAY) + minutes / 60;
        if (hour >= 18) hour = 18;
        if (hour <= 6) hour = 6;
        angle = ((hour - 6) / 12) * 180f;
    }

    public void setViewProperty(String sr,String ss){
        this.sr = sr;
        this.ss = ss;
       invalidate();
    }
}
