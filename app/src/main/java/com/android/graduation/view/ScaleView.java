package com.android.graduation.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.android.graduation.R;
import com.android.graduation.utils.LogUtil;


public class ScaleView extends View{

    private float mCenterX;
    private float mCenterY;

    private float mRadius;
    private float mLineLength;

    private boolean isUpdate = false;

    //数据类型
    private int mType;
    private static final int TYPE_TEMP_RANGE = 1;
    private static final int TYPE_POLLUTION_INDEX = 2;
    private static final int TYPE_HUMIDITY = 3;
    private String mDesc;
    private int[] mValueRange;

    private int[] mColorArray;
    private Shader mWhiteShader,mColorShader;

    private Paint mLinePaint;
    private Paint mTxtPaint;

    private int startAngle = 320;
    private int endAngle  = 80;


    //中心文字的宽高
    private int textWidth;
    private int textHeight;

    public ScaleView(Context context) {
        super(context);
        init();
    }

    public ScaleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScaleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //刻度绘笔
        mLinePaint = new Paint();
        mLinePaint.setStrokeWidth(3);
        mLinePaint.setAntiAlias(true);
        //文字绘笔
        mTxtPaint = new  TextPaint();
        mTxtPaint.setColor(Color.WHITE);
        mTxtPaint.setStrokeWidth(3);
        mTxtPaint.setAntiAlias(true);

        mType = 1;
        mValueRange = new int[]{12,25,20};
        mDesc = "优";

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec,widthMeasureSpec);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (double angle = 0; angle <= 360d; angle+= 3.0d){
            float insideRadius = mRadius - mLineLength;
            float xStart = calculateX(angle,mRadius);
            float xEnd   = calculateX(angle,insideRadius);

            float yStart = calculateY(angle,mRadius);
            float yEnd   = calculateY(angle,insideRadius);

            setShaderRange(angle);
            if (angle == 156 || angle == 204){
                mLinePaint.setStrokeWidth(1);
                float xStartL = calculateX(angle,mRadius * 1.05f);
                float xEndL   = xEnd;
                float yStartL = calculateY(angle,mRadius * 1.05f);
                float yEndL   = yEnd;
                canvas.drawLine(xStartL,yStartL,xEndL,yEndL,mLinePaint);
            }else if(! (angle > 156 && angle < 204)){
                mLinePaint.setStrokeWidth(3);
                canvas.drawLine(xStart,yStart,xEnd,yEnd,mLinePaint);
            }
        }
        drawCenterText(canvas);
        drawCircleByValue(canvas);
        if (isUpdate){
            drawOtherByType(canvas);
        }
    }

    private void drawCircleByValue(Canvas canvas) {
        float value;
        float length = (mRadius - mLineLength) * 0.9f;
        switch (mType){
            case TYPE_TEMP_RANGE:
                value = 320 + 120 * ((float)(mValueRange[2] - mValueRange[0]) / (float)(mValueRange[1] - mValueRange[0]));
                if (value >= 360){
                    value -= 360;
                }
                canvas.drawCircle(calculateX(value,length),calculateY(value,length),mRadius * 0.04f,mLinePaint);
                break;
            case TYPE_POLLUTION_INDEX:
                value = 204 + 0.624f * mValueRange[2];
                if (value >= 360){
                    value -= 360;
                }
                canvas.drawCircle(calculateX(value,length),calculateY(value,length),mRadius * 0.04f,mLinePaint);
                break;
            case TYPE_HUMIDITY:
                value = 204 + 3.12f * mValueRange[2];
                if (value >= 360){
                    value -= 360;
                }
                mLinePaint.setShader(mColorShader);
                canvas.drawCircle(calculateX(value,length),calculateY(value,length),mRadius * 0.04f,mLinePaint);
                break;
        }
    }

    private void drawOtherByType(Canvas canvas) {
        switch (mType){
            case TYPE_TEMP_RANGE:
                drawStartAndEndTemp(canvas);
                drawIcon(canvas);
                break;
            case TYPE_POLLUTION_INDEX:
                drawValueRangeText("500",canvas);
                drawGradeText(mDesc,canvas);
                break;
            case TYPE_HUMIDITY:
                drawValueRangeText("100",canvas);
                break;
        }
    }

    private void drawGradeText(String desc, Canvas canvas) {
        Rect mTextRect = new Rect();
        mTxtPaint.setTextSize(mLineLength);
        mTxtPaint.getTextBounds(desc, 0, desc.length(), mTextRect);
        int valueWidth = mTextRect.width();
        int valueHeight = mTextRect.height();
        canvas.drawText(desc,mCenterX - valueWidth / 2, mCenterY + textHeight , mTxtPaint );
    }

    private void drawValueRangeText(String value,Canvas canvas) {
        Rect mTextRect = new Rect();
        mTxtPaint.setTextSize(mLineLength);
        mTxtPaint.getTextBounds(value, 0, value.length(), mTextRect);
        int valueWidth = mTextRect.width();
        int valueHeight = mTextRect.height();
        canvas.drawText(value,calculateX(156,mRadius - mLineLength), mCenterY + mRadius + valueHeight, mTxtPaint);

        canvas.drawText("0",calculateX(210,mRadius),mCenterY + mRadius + valueHeight, mTxtPaint);
    }

    private void drawIcon(Canvas canvas) {

        BitmapDrawable weather = (BitmapDrawable) getResources().getDrawable(R.drawable.weather_rain);
        float width = weather.getBitmap().getWidth() / 1.5f;
        float height = weather.getBitmap().getHeight() / 1.5f;
        float left = mCenterX - width / 2f;
        float top  = calculateY(156,mRadius - mLineLength * 1.5f);
        float right = left + width;
        float bottom = top + height;
       weather.setBounds((int)left,(int)top,(int)right,(int)bottom);
        weather.draw(canvas);

        BitmapDrawable degree = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_degree_big);
        float left1 = mCenterX + textWidth / 1.98f;
        float top1  = mCenterY - textHeight/2;
        float right1 = left1 + degree.getBitmap().getWidth();
        float bottom1 = top1 + degree.getBitmap().getHeight();
        degree.setBounds((int)left1,(int)top1,(int)right1,(int)bottom1);
        degree.draw(canvas);

    }

    private void drawStartAndEndTemp(Canvas canvas) {
        mTxtPaint.setTextSize(mRadius * 0.1f);
        canvas.drawText(mValueRange[0] + "°",calculateX(startAngle,mRadius * 1.1f),
                calculateY(startAngle,mRadius * 1.1f),mTxtPaint);
        canvas.drawText(mValueRange[1] + "°",calculateX(endAngle,mRadius * 1.05f),
                calculateY(endAngle,mRadius * 1.1f),mTxtPaint);
    }

    private void setShaderRange(double angle){
        switch (mType){
            case TYPE_TEMP_RANGE:
                if (angle >= startAngle || angle <= endAngle){
                    mLinePaint.setShader(mColorShader);
                }else {
                    mLinePaint.setShader(mWhiteShader);
                }
                break;
            case TYPE_POLLUTION_INDEX:
                mLinePaint.setShader(mColorShader);
                break;
            case TYPE_HUMIDITY:
                if (mValueRange[2] <= 50){
                    if (angle > 204 && angle <= mValueRange[2] * 3.12f + 204){
                        mLinePaint.setShader(mColorShader);
                    }else
                        mLinePaint.setShader(mWhiteShader);
                }else{
                    if (angle > mValueRange[2] * 3.12f - 156 && angle < 204){
                        mLinePaint.setShader(mWhiteShader);
                    }else
                        mLinePaint.setShader(mColorShader);
                }
                break;
        }
    }

    private void drawCenterText(Canvas canvas){
        String centerText;
        Rect mTextRect = new Rect();
        if (mType == TYPE_HUMIDITY){
            centerText = mValueRange[2] + "%";
            mTxtPaint.setTextSize(mRadius * 0.4f);
        }else{
            centerText = String.valueOf(mValueRange[2]);
            mTxtPaint.setTextSize(mRadius * 0.6f);
        }
        mTxtPaint.setTypeface(Typeface.DEFAULT);
        mTxtPaint.getTextBounds(centerText, 0, centerText.length(), mTextRect);
        textWidth = mTextRect.width();
        textHeight = mTextRect.height();
        canvas.drawText(centerText, mCenterX - textWidth / 2, mCenterY + mTextRect.height() / 2, mTxtPaint);

    }

    private float calculateX(double angle,float length){
        angle = angle * ((2 * Math.PI) / 360);
        double x = length * Math.sin(angle) + mCenterX;
        return (float)x;
    }

    private float calculateY(double angle,float lengh){
        angle = angle * ((2 * Math.PI) / 360);
        double y = mCenterY - lengh * Math.cos(angle);
        return (float)y;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        float mViewWidth = getWidth();
        float mViewHeight = getHeight();

        mCenterX = mViewWidth/2f;
        mCenterY = mViewHeight/2f;

        mRadius = Math.min(mViewWidth,mViewHeight) * 0.4f;
        mLineLength = mRadius * 0.15f;

        mColorShader = getSweepGradient();
        mWhiteShader = new SweepGradient(mCenterX,mCenterY,new int[] {Color.WHITE,Color.WHITE},null);
        //设定渲染器的初始位置
        Matrix matrix = new Matrix();
        matrix.setRotate(68,mCenterX,mCenterY);
        mColorShader.setLocalMatrix(matrix);
        //刻度线设置渲染器
        mLinePaint.setShader(mColorShader);


    }

 /*   @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        float mViewWidth = getWidth();
        float mViewHeight = getHeight();

        mCenterX = mViewWidth/2f;
        mCenterY = mViewHeight/2f;

        mRadius = Math.min(mViewWidth,mViewHeight) * 0.4f;
        mLineLength = mRadius * 0.15f;

        mColorShader = getSweepGradient();
        mWhiteShader = new SweepGradient(mCenterX,mCenterY,new int[] {Color.WHITE,Color.WHITE},null);
        //设定渲染器的初始位置
        Matrix matrix = new Matrix();
        matrix.setRotate(66,mCenterX,mCenterY);
        mColorShader.setLocalMatrix(matrix);
        //刻度线设置渲染器
        mLinePaint.setShader(mColorShader);

        LogUtil.e("scaleView","onWindowFocusChanged");

        super.onWindowFocusChanged(hasWindowFocus);
    }
*/

    public void setViewProperty(int type,int[] valueRange,String desc){
        mType = type;
        mValueRange = valueRange;
        mDesc = desc;
        isUpdate = true;
        invalidate();
    }

    private SweepGradient getSweepGradient(){
        switch (mType){
            case TYPE_TEMP_RANGE:
            case TYPE_POLLUTION_INDEX:
                return new SweepGradient(mCenterX,mCenterY,new int[] { Color.parseColor("#3D84F7"),Color.parseColor("#4BBDA3"),
                    Color.parseColor("#73C740"), Color.parseColor("#DB904A"),Color.parseColor("#792B30") }, null);

            case TYPE_HUMIDITY:
                return new SweepGradient(mCenterX,mCenterY,new int[] {Color.parseColor("#17CFDA"),Color.parseColor("#17CFDA")},null);
            default:
                return null;
        }
    }




}
