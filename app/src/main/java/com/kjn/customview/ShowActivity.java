package com.kjn.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String stringExtra = getIntent().getStringExtra(MainActivity.KEY);
        if (MainActivity.TIME_AXIS.equals(stringExtra)) {
            setContentView(R.layout.view_timeaxis);
            initTimeAxis();
        } else if (MainActivity.SHOPPING_BUTTON.equals(stringExtra)) {
            setContentView(R.layout.view_shoppingbutton);
        } else {
            setContentView(R.layout.activity_show);
        }
    }

    private void initTimeAxis() {
        TimeAxis timeAxis = (TimeAxis) findViewById(R.id.my_view);

        List<String> mDatas = new ArrayList<>();
        List<String> mTimes = new ArrayList<>();

        mDatas.add("XXX已发快递到泉州");
        mDatas.add("泉州快递员XXX收到");
        mDatas.add("XXX收到转发给XXX");
        mDatas.add("XXX从泉州发往XXX");
        mDatas.add("快递已到厦门");
        mDatas.add("快递员XXXX收到");
        mDatas.add("XXX转发给KJN");
        mDatas.add("快递从厦门发往杭州");
        mDatas.add("KJN已签收！");

        mTimes.add("2017-1-1");
        mTimes.add("2017-1-2");
        mTimes.add("2017-1-3");
        mTimes.add("2017-1-4");
        mTimes.add("2017-1-5");
        mTimes.add("2017-1-6");
        mTimes.add("2017-1-7");
        mTimes.add("2017-1-8");
        mTimes.add("2017-1-9");

        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int heightPixels = displayMetrics.heightPixels;
        int widthPixels = displayMetrics.widthPixels;
        //屏幕宽度、屏幕高度、红色标签数量、数据1、数据2
        timeAxis.setDatas(heightPixels, widthPixels, 2, mDatas, mTimes);
    }
}
