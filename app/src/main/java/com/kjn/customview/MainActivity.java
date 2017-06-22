package com.kjn.customview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String KEY = "KEY";
    public static final String TIME_AXIS = "TIME_AXIS";
    public static final String SHOPPING_BUTTON = "SHOPPING_BUTTON";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void start(Context context, String str) {
        Intent starter = new Intent(context, ShowActivity.class);
        starter.putExtra(KEY, str);
        context.startActivity(starter);
    }

    public void click(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                start(this, TIME_AXIS);
                break;
            case R.id.btn2:
                start(this, SHOPPING_BUTTON);
                break;
        }
    }
}
