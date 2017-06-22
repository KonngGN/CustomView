package com.kjn.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Konng on 2017/6/6 17:45
 * 邮箱：197726885@qq.com
 * 说明：
 * 详细：
 */

public class TimeAxis extends View {


    private Paint mPaint1;
    private Paint mPaint2;
    private Paint mPaint3;
    private Paint mPaint4;
    private int mColor = Color.BLACK;
    private int mHeightPixels;
    private int mWidthPixels;
    private int mCount;
    private int mRedCount;
    private List<String> mDatas;
    private List<String> mTimes;
    //行高
    private static final int COUNT_DISTANCE = 200;
    //外圆环厚度
    private static final int THICKNESS = 10;
    //外圆半径
    private static final int EXRADIUS = COUNT_DISTANCE / 10;
    //内圆半径
    private static final int INT_RADIUS = EXRADIUS - THICKNESS;


    public TimeAxis(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        //画直线
        mPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint1.setStyle(Paint.Style.STROKE);
        mPaint1.setColor(Color.GRAY);
        mPaint1.setStrokeWidth(5);
        //画内圆
        mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint2.setStyle(Paint.Style.FILL);
        mPaint2.setColor(mColor);
        mPaint2.setStrokeWidth(1);
        //画外圆环，setStrokeWidth 是宽度
        mPaint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint3.setStyle(Paint.Style.STROKE);
        mPaint3.setColor(Color.GRAY);
        mPaint3.setStrokeWidth(THICKNESS);
        //画直线
        mPaint4 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint4.setStyle(Paint.Style.FILL);
        mPaint4.setColor(Color.GRAY);
        mPaint4.setStrokeWidth(1);
        mPaint4.setTextSize(50);

        mDatas = new ArrayList<>();
        mTimes = new ArrayList<>();
    }

    /**
     *
     * @param color 设置文本和内圆的颜色
     */
    public void setColor(int color) {
        mColor = color;
        mPaint2.setColor(mColor);
        mPaint4.setColor(mColor);
    }

    /**
     *
     * @param heightPixels 屏幕宽度 px
     * @param widthPixels 屏幕高度 px
     * @param redCount 红色标签数量
     * @param data 文本详情内容
     * @param time 文本时间内容
     */
    public void setDatas(int heightPixels, int widthPixels, int redCount, List<String> data, List<String> time) {
        mHeightPixels = heightPixels;
        mWidthPixels = widthPixels;
        mCount = data.size();
        mRedCount = redCount;
        mDatas = data;
        mTimes = time;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 这里的数字你自己改改看
        for (int i = 0; i < mCount; i++) {
            if (mRedCount > i) {
                setColor(Color.RED);
            } else {
                setColor(Color.GRAY);
            }
            //起始位置：宽度（屏幕宽度的十分之一）、高度（行高*i+100）、文本数据、时间数据
            drawView(canvas, mWidthPixels / 10, COUNT_DISTANCE * i + COUNT_DISTANCE / 3, mDatas.get(i), mTimes.get(i));

            if (i < mCount - 1) {
                drawLine(canvas, mWidthPixels / 10, COUNT_DISTANCE * i + COUNT_DISTANCE / 3, COUNT_DISTANCE * (i + 1) + COUNT_DISTANCE / 3 - EXRADIUS);
            }
        }
    }


    private void drawView(Canvas canvas, int cx, int cy, String data, String time) {
        //内圆
        canvas.drawCircle(cx, cy, INT_RADIUS, mPaint2);
        //外圆
        canvas.drawCircle(cx, cy, EXRADIUS, mPaint3);
        //文字
        canvas.drawText(data, cx + COUNT_DISTANCE / 3, cy, mPaint4);
        //时间
        canvas.drawText(time, cx + COUNT_DISTANCE / 3, cy + COUNT_DISTANCE / 3, mPaint4);
    }
    //横线
    private void drawLine(Canvas canvas, int cx, int cy, int stopY) {
        canvas.drawLine(cx, cy + EXRADIUS, cx, stopY, mPaint1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //这里自己可以动态获取，这是屏幕尺寸的宽高，单位 px
        setMeasuredDimension(mWidthPixels, mCount * COUNT_DISTANCE);
    }
}
