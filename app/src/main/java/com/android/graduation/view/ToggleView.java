package com.android.graduation.view;

/**
 * Created by asus on 2017/4/12.
 */

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.android.graduation.R;

/**
 * Created by Andy on 2016/8/18.
 */
public class ToggleView extends View
        implements Animator.AnimatorListener,
        ValueAnimator.AnimatorUpdateListener {

    /*默认toggle关闭的颜色*/
    private static final int DEFAULT_NORMAL_COLOR = Color.WHITE;

    /*默认toggle打开的颜色*/
    private static final int DEFAULT_ACTIVE_COLOR = Color.parseColor("#18888a");

    /*背景色*/
    private static final int DEFAULT_BACKGROUND_COLOR = Color.parseColor("#8a8a8a");

    /*默认的阴影高度*/
    private static final int DEFAULT_SHADOW_HEIGHT = 12;

    /*宽度*/
    private static final int DEFAULT_WIDTH = 42;

    /*高度*/
    private static final int DEFAULT_HEIGHT = 26;

    /*背景比例*/
    private static final float DEFALUT_BACKGROUND_RATE = 0.7818f;

    /*背景相对于前景的缩进值*/
    private static final float DEFAULT_PADDING_HOR = 2;

    /*关闭状态*/
    public static final boolean STATE_CLOSE = false;

    /*打开状态*/
    public static final boolean STATE_OPEN = true;

    /*动画时长*/
    private static final int ANIMATOR_DURATION = 120;

    /*控件前景色*/
    private int mActiveColor;
    /*控件背景色*/
    private int mBackgroundColor;
    /*控件状态*/
    private boolean mState;
    /*控件的背景范围*/
    private RectF mBackgroundRect;
    /*背景比例*/
    private float mBackgroundRate;
    /*前景相对于背景的高度差*/
    private float mLeftSize;
    /*前景半径*/
    private float mForeRadius;
    /*前景高度*/
    private float mForeHeight;
    /*前景动画*/
    private ValueAnimator mAnimator;
    /*前景颜色动画*/
    private ValueAnimator mForeColorAnimator;
    /*背景景颜色动画*/
    private ValueAnimator mBackColorAnimator;

    /*动画是否正在执行*/
    private boolean mIsAnimatorStarting = false;
    /*动画是否是从左往右*/
    private boolean mIsLeftToRight = true;
    /*当前动画的值*/
    private int mCurrentIntValue;
    /*当前前景色*/
    private int mCurrentForeColor;
    /*当前背景色*/
    private int mCurrentBackColor;

    private Paint mPaint;

    private OnStateChangeListener mListener;

    public ToggleView(Context context) {
        super(context);
        init(context, null);
    }

    public ToggleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ToggleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (MeasureSpec.EXACTLY == widthMode) {
            height = width * DEFAULT_HEIGHT / DEFAULT_WIDTH;
        } else {
            if (MeasureSpec.EXACTLY == heightMode) {
                width = height * DEFAULT_WIDTH / DEFAULT_HEIGHT;
            } else {
                width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_WIDTH, getContext().getResources().getDisplayMetrics());
                height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_HEIGHT, getContext().getResources().getDisplayMetrics());
            }
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh) {
        super.onSizeChanged(width, height, oldw, oldh);

        caculateBaseInfo(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int halfHeight = height / 2;

        if (mIsAnimatorStarting) {
            /*画背景*/
            mPaint.setColor(mCurrentBackColor);
            canvas.drawRoundRect(mBackgroundRect, mBackgroundRect.height() / 2, mBackgroundRect.height() / 2, mPaint);
            /*画前景*/
            mPaint.setColor(mCurrentForeColor);
            canvas.drawOval(new RectF(mCurrentIntValue, DEFAULT_SHADOW_HEIGHT + mLeftSize / 2, mCurrentIntValue + mForeHeight + DEFAULT_SHADOW_HEIGHT, height - DEFAULT_SHADOW_HEIGHT - mLeftSize / 2), mPaint);
        } else {
            mPaint.setColor(STATE_CLOSE == mState ? DEFAULT_BACKGROUND_COLOR : mBackgroundColor);
            canvas.drawRoundRect(mBackgroundRect, mBackgroundRect.height() / 2, mBackgroundRect.height() / 2, mPaint);

//            mPaint.setColor(mState == STATE_CLOSE ? DEFAULT_NORMAL_COLOR : mActiveColor);
            mPaint.setColor(DEFAULT_NORMAL_COLOR);
            mPaint.setShadowLayer(DEFAULT_SHADOW_HEIGHT, 0, DEFAULT_SHADOW_HEIGHT / 2, Color.parseColor("#88888888"));
            canvas.drawCircle(mState == STATE_CLOSE ? mForeRadius + DEFAULT_SHADOW_HEIGHT - DEFAULT_PADDING_HOR : width - DEFAULT_SHADOW_HEIGHT - mForeRadius + DEFAULT_PADDING_HOR, halfHeight, mForeRadius, mPaint);
            mPaint.clearShadowLayer();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_UP:
                if (0 < event.getX() && getMeasuredWidth() > event.getX() && 0 < event.getY() && getMeasuredHeight() > event.getY()) {
                    performStateChange();
                    super.onTouchEvent(event);
                    return true;
                }
                break;

        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onAnimationStart(Animator animation) {
        mIsAnimatorStarting = true;
        invalidate();
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        mIsAnimatorStarting = false;
        mAnimator = null;
        mForeColorAnimator = null;
        mBackColorAnimator = null;
        invalidate();

        mListener.onToggleStateChanged(getToggleState());
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        if (mAnimator == animation) {
            mCurrentIntValue = (int) animation.getAnimatedValue();
        } else if (mForeColorAnimator == animation) {
            mCurrentForeColor = (int) animation.getAnimatedValue();
        } else {
            mCurrentBackColor = (int) animation.getAnimatedValue();
        }
        invalidate();
    }

    public void setActiveColor(int color) {
        mActiveColor = color;
    }

    /*基本初始化*/
    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SimpleToggle);
        mActiveColor = typedArray.getColor(R.styleable.SimpleToggle_activeColor, DEFAULT_ACTIVE_COLOR);
//        mState = typedArray.getInt(R.styleable.SimpleToggle_toggleState, STATE_CLOSE);
        mBackgroundRate = typedArray.getFloat(R.styleable.SimpleToggle_backgroundRate, DEFALUT_BACKGROUND_RATE);
        typedArray.recycle();

        mBackgroundColor = getBackgroundColor(mActiveColor);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setClickable(true);
    }

    private int getBackgroundColor(int color) {
        int red = Color.red(color) + 70;
        int green = Color.green(color) + 70;
        int blue = Color.blue(color) + 70;

        return Color.rgb(red > 255 ? 255 : red, green > 255 ? 255 : green, blue > 255 ? 255 : blue);
    }

    /*计算基本数据*/
    private void caculateBaseInfo(int width, int height) {
        mForeRadius = height / 2 - DEFAULT_SHADOW_HEIGHT;
        mForeHeight = mForeRadius * 2;

        /*防止数据错误导致的视图异常*/
        if (mBackgroundRate <= 0.1) {
            mBackgroundRate = 0.1f;
        } else if (mBackgroundRate >= 1.0f) {
            mBackgroundRate = 1.0f;
        }
        float leftRate = (1.0f - mBackgroundRate) / 2;

        mLeftSize = leftRate * mForeHeight;
        float paddingHor = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_PADDING_HOR, getContext().getResources().getDisplayMetrics());

        mBackgroundRect = new RectF(DEFAULT_SHADOW_HEIGHT + paddingHor, DEFAULT_SHADOW_HEIGHT + mLeftSize, width - DEFAULT_SHADOW_HEIGHT - paddingHor, height - DEFAULT_SHADOW_HEIGHT - mLeftSize);
    }

    /*执行前景色动画*/
    private void startForeAnimator() {
        /*mAnimator = mIsLeftToRight ? ValueAnimator.ofInt(0, 1) : ValueAnimator.ofInt(1, 0);*/
        mAnimator = mIsLeftToRight ? ValueAnimator.ofInt(0, (int) (getMeasuredWidth() - mForeHeight - DEFAULT_SHADOW_HEIGHT)) : ValueAnimator.ofInt((int) (getMeasuredWidth() - mForeHeight - DEFAULT_SHADOW_HEIGHT), 0);
        mAnimator.setDuration(ANIMATOR_DURATION);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.addUpdateListener(this);
        mAnimator.addListener(this);
        mAnimator.start();
    }

    /*执行背景色动画*/
    private void startColorAnimator() {
        int startForeColor = 0;
        int startBackColor = 0;
        int endForeColor = 0;
        int endBackColor = 0;

        if (mIsLeftToRight) {
            startForeColor = Color.WHITE;
            startBackColor = DEFAULT_BACKGROUND_COLOR;
            endForeColor = mActiveColor;
            endBackColor = mBackgroundColor;
        } else {
            startForeColor = mActiveColor;
            startBackColor = mBackgroundColor;
            endForeColor = Color.WHITE;
            endBackColor = DEFAULT_BACKGROUND_COLOR;
        }
        mForeColorAnimator = ValueAnimator.ofInt(startForeColor, endForeColor);
        mBackColorAnimator = ValueAnimator.ofInt(startBackColor, endBackColor);

        mForeColorAnimator.setDuration(ANIMATOR_DURATION);
        mBackColorAnimator.setDuration(ANIMATOR_DURATION);

        mForeColorAnimator.setInterpolator(new LinearInterpolator());
        mBackColorAnimator.setInterpolator(new LinearInterpolator());

        mForeColorAnimator.setEvaluator(new ArgbEvaluator());
        mBackColorAnimator.setEvaluator(new ArgbEvaluator());

        mForeColorAnimator.addUpdateListener(this);
        mBackColorAnimator.addUpdateListener(this);

        mForeColorAnimator.start();
        mBackColorAnimator.start();
    }

    private void performStateChange() {
        if (mState == STATE_CLOSE) {
            mState = STATE_OPEN;
            mIsLeftToRight = true;
        } else {
            mState = STATE_CLOSE;
            mIsLeftToRight = false;
        }
        startForeAnimator();
        startColorAnimator();
    }

    public void setToggleState(boolean state){
        mState = state;
        invalidate();
    }

    public boolean getToggleState(){
        return mState;
    }

    public void setOnStateChangeListener(OnStateChangeListener listener){
        mListener = listener;
    }

    public interface OnStateChangeListener{
        void onToggleStateChanged(boolean state);
    }

}
