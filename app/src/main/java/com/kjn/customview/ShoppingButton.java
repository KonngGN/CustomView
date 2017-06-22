package com.kjn.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 作者：Konng on 2017/6/22 11:22
 * 邮箱：197726885@qq.com
 * 说明：
 * 详细：尺寸设置根据圆的半径来变化
 */

public class ShoppingButton extends View {
    //左边圆画笔
    private Paint mCirclePaint1;
    //右边圆画笔
    private Paint mCirclePaint2;
    //圆心符号
    private Paint mCirclePaint3;
    //文字画笔
    private Paint mTextPaint;
    //初始化文字
    private String text = "0";
    //文字数字，用于转化String
    private int num;
    //文字颜色
    private int mTexColor;
    //圆心颜色
    private int mCenterCircleColor;
    //圆的颜色
    private int mCircleColor;
    //圆的半径
    private int mRadius;
    //两个圆中间的位置
    private int mDistance;
    //布局宽度
    private int mWidth;
    //布局高度
    private int mHight;
    //测量宽度，是XML中设置大小失效
    private int measureWidth = 240;
    //测量高度，是XML中设置大小失效
    private int measureHight = 90;
    //判断点击后是否松开
    private boolean isCheck;


    public ShoppingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }

    private void init(Context context, AttributeSet attrs) {


        //获取 XML 中的设置，可以设置字体颜色、半径、间距
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ShoppingButton);
        mTexColor = a.getColor(R.styleable.ShoppingButton_textColor, Color.GRAY);
        mCenterCircleColor = a.getColor(R.styleable.ShoppingButton_CenterCircleColor, Color.GRAY);
        mCircleColor = a.getColor(R.styleable.ShoppingButton_CircleColor, Color.GRAY);
        mRadius = a.getInt(R.styleable.ShoppingButton_radius, 10);
        mDistance = a.getInt(R.styleable.ShoppingButton_distance, mRadius * 5);
        a.recycle();


        mCirclePaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint1.setStyle(Paint.Style.STROKE);
        mCirclePaint1.setStrokeWidth(5);
        mCirclePaint1.setColor(mCircleColor);

        mCirclePaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint2.setStyle(Paint.Style.STROKE);
        mCirclePaint2.setStrokeWidth(5);
        mCirclePaint2.setColor(mCircleColor);

        mCirclePaint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint3.setStyle(Paint.Style.STROKE);
        mCirclePaint3.setStrokeWidth(5);
        mCirclePaint3.setColor(mCenterCircleColor);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setStrokeWidth(1);
        mTextPaint.setColor(mTexColor);
        mTextPaint.setTextSize(mRadius * 2);
        //设置硬件加速关闭，否则设置阴影无效
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRGB(100, 255, 255);
        //起始位置
        mWidth = (int) (getPaddingLeft() + mRadius * 1.5);
        mHight = (int) (getPaddingTop() + mRadius * 1.5);


        if (getTextWidth() > (mDistance - mRadius * 2)) {
            mDistance += mRadius;
        }
        canvas.drawCircle(mWidth, mHight, mRadius, mCirclePaint1);
        canvas.drawLine(mWidth - mRadius / 2, mHight, mWidth + mRadius / 2, mHight, mCirclePaint3);

        canvas.drawCircle(mWidth + mDistance, mHight, mRadius, mCirclePaint2);
        canvas.drawLine(mWidth + mDistance - mRadius / 2, mHight, mWidth + mDistance + mRadius / 2, mHight, mCirclePaint3);
        canvas.drawLine(mWidth + mDistance, mHight - mRadius / 2, mWidth + mDistance, mHight + mRadius / 2, mCirclePaint3);

        canvas.drawText(text, mWidth + mDistance / 2 - getTextWidth() / 2, mHight + getTextHeight() / 2, mTextPaint);
    }

    //文字宽度
    private int getTextWidth() {
        return (int) mTextPaint.measureText(text);
    }

    //文字高度
    private int getTextHeight() {
        Rect rect = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), rect);
        return rect.height();
    }

    //加
    public void addText() {
        num++;
        text = String.valueOf(num);
        mCirclePaint2.setShadowLayer(1, 0, 0, Color.RED);//设置阴影
        invalidate();
    }

    //减
    public void reduceText() {
        if (num > 0) {
            num--;
            text = String.valueOf(num);
        }
        mCirclePaint1.setShadowLayer(1, 0, 0, Color.DKGRAY);//设置阴影
        invalidate();
    }
    //获取数字
    public int getTextNum() {
        return num;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //第一个圆内点击响应
                if (x >= (mWidth - mRadius) && x <= (mWidth + mRadius) && y >= (mHight - mRadius) && y <= (mHight + mRadius)) {
                    reduceText();
                    isCheck = true;
                    //第二个圆内点击响应
                } else if (x >= (mWidth + mDistance - mRadius) && x <= (mWidth + mDistance + mRadius) && y >= (mHight - mRadius) && y <= (mHight + mRadius)) {
                    addText();
                    isCheck = true;
                }
                //拦截住点击事件，直到松开
                return true;
            case MotionEvent.ACTION_MOVE:
                return true;
            case MotionEvent.ACTION_UP:
                if (isCheck) {
                    mCirclePaint1.setShadowLayer(0, 0, 0, Color.TRANSPARENT);
                    mCirclePaint2.setShadowLayer(0, 0, 0, Color.TRANSPARENT);
                    invalidate();
                    isCheck = false;
                }
                //松开
                return false;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //设置 wrap_content 时固定大小
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mRadius * 8, mRadius * 3);//单位是 px
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mRadius * 8, mRadius * 3);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mRadius * 8, mRadius * 3);
        }
        Log.d("widthSize", "widthSize: " + widthSize + "heightSize:" + heightSize);
    }




}
