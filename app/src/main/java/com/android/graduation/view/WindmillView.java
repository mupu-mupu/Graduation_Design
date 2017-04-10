package com.android.graduation.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.android.graduation.R;

/**
 * Created by asus on 2017/4/6.
 */

public class WindmillView extends View{

    private float mWidth;
    private float mHeight;

    //大风车叶子的宽高
    private float bigBladeWidth;
    private float bigBladeHeight;
    private float bigPoleWidth;
    private float bigPoleHeight;

    //大风车叶子的宽高
    private float bladeWidth;
    private float bladeHeight;
    private float poleWidth;
    private float poleHeight;

    private BitmapDrawable bigBlade;
    private BitmapDrawable bigPole;
    private BitmapDrawable blade;
    private BitmapDrawable pole;

    private float degree;
    private float degree1;
    private float speed = 2f;

    public WindmillView(Context context) {
        super(context);
    }

    public WindmillView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WindmillView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        setMeasuredDimension();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = getWidth();
        mHeight = getHeight();

        super.onSizeChanged(w, h, oldw, oldh);
    }

    /*    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {

        mWidth = getWidth();
        mHeight = getHeight();

        super.onWindowFocusChanged(hasWindowFocus);
    }*/

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBigBlade(canvas);
        drawBigPole(canvas);
        drawPole(canvas);
        drawBlade(canvas);

        invalidate();
    }

    private void drawBigPole(Canvas canvas) {

        if (bigPole == null){
            bigPole = (BitmapDrawable) getResources().getDrawable(R.drawable.windmill);
            bigPoleHeight = bigPole.getBitmap().getHeight();
            bigPoleWidth = bigPole.getBitmap().getWidth();
            float left = bigBladeWidth / 2  - bigPoleWidth / 2;
            float top  = bigBladeHeight / 2 - bigPoleWidth / 3.1f;
            float right = left + bigPoleWidth;
            float bottom = top + bigPoleHeight;
            bigPole.setBounds((int)left,(int)top,(int)right,(int)bottom);
        }
        bigPole.draw(canvas);
    }

    private void drawBigBlade(Canvas canvas) {
        canvas.save();
        if (bigBlade == null){
            bigBlade = (BitmapDrawable) getResources().getDrawable(R.drawable.windmill_blade);
            bigBladeHeight = bigBlade.getBitmap().getHeight();
            bigBladeWidth = bigBlade.getBitmap().getWidth();
            float left = 0;
            float top  = 0;
            float right = left + bigBladeWidth;
            float bottom = top + bigBladeHeight;
            bigBlade.setBounds((int)left,(int)top,(int)right,(int)bottom);
        }
        canvas.rotate(degree += speed,bigBladeWidth / 2,bigBladeHeight / 2);
        if (degree >= 360)
            degree = 0;
        bigBlade.draw(canvas);
        canvas.restore();
    }

    private void drawBlade(Canvas canvas) {
        canvas.save();
        if (blade == null){
            blade = (BitmapDrawable) getResources().getDrawable(R.drawable.windmill_blade_small);
            bladeHeight = blade.getBitmap().getHeight();
            bladeWidth = blade.getBitmap().getWidth();
            float left = bigBladeWidth * 0.9f + poleWidth / 2 - bladeWidth / 2;
            float top  = bigBladeHeight / 2 - bigPoleWidth / 3.1f + bigPoleHeight - poleHeight - bladeHeight / 2 + poleWidth / 3.1f;
            float right = left + bladeWidth;
            float bottom = top + bladeHeight;
            blade.setBounds((int)left,(int)top,(int)right,(int)bottom);
        }
        canvas.rotate(degree1 += speed,bigBladeWidth * 0.9f + poleWidth / 2,
                bigBladeHeight / 2 - bigPoleWidth / 3.1f + bigPoleHeight - poleHeight + poleWidth / 3.1f);
        if (degree1 >= 360)
            degree1 = 0;
        blade.draw(canvas);
        canvas.restore();
    }


    private void drawPole(Canvas canvas) {
        if (pole == null){
            pole = (BitmapDrawable) getResources().getDrawable(R.drawable.windmill_small);
            poleHeight = pole.getBitmap().getHeight();
            poleWidth = pole.getBitmap().getWidth();
            float left = bigBladeWidth * 0.9f;
            float bottom =  bigBladeHeight / 2 - bigPoleWidth / 3.1f + bigPoleHeight;
            float top  = bottom - poleHeight;
            float right = left + poleWidth;

            pole.setBounds((int)left,(int)top,(int)right,(int)bottom);
        }
        pole.draw(canvas);
    }


}
