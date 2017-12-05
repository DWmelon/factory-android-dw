package com.yongheng;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by melon on 2017/6/19.
 */

public class HeatMonitorLayout extends LinearLayout {

    int countH = 15;
    int level = 0;


    int widthEach;

    private LinearLayout mLlHeatDay;

    private List<Integer> textColorList = new ArrayList<Integer>(){{
        add(R.color.heat_chart_blue_text);
        add(R.color.heat_chart_green_text);
        add(R.color.heat_chart_red_text);
    }};

    public HeatMonitorLayout(Context context) {
        super(context);
        init();
    }

    public HeatMonitorLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_heat_monitor,this,true);
        mLlHeatDay = (LinearLayout) view.findViewById(R.id.ll_heat_monitor_day);

        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        widthEach = (metrics.widthPixels - 2 * getResources().getDimensionPixelOffset(R.dimen.margin_15))/(countH + 2);


        int count = countH/2;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime()- count * 2 * 1000 * 60 * 60 * 24);
        int month;
        int day;
        TextView textView;
        for (int i = 0;i < count;i++){
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            String str = month+"."+day;

            textView = new TextView(getContext());
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,11);
            textView.setText(str);
            textView.setTextColor(getResources().getColor(R.color.sub_title_color));
            LayoutParams params = new LayoutParams(widthEach, ViewGroup.LayoutParams.WRAP_CONTENT);
            mLlHeatDay.addView(textView,params);
            textView = new TextView(getContext());
            LayoutParams params1 = new LayoutParams(widthEach, ViewGroup.LayoutParams.WRAP_CONTENT);
            mLlHeatDay.addView(textView,params1);

            calendar.add(Calendar.DAY_OF_MONTH,2);
        }

        textView = new TextView(getContext());
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,11);
        textView.setText("今天");
        textView.setTextColor(getResources().getColor(textColorList.get(level)));
        LayoutParams params = new LayoutParams(widthEach, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLlHeatDay.addView(textView,params);
        textView = new TextView(getContext());
        LayoutParams params1 = new LayoutParams(widthEach, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLlHeatDay.addView(textView,params1);

        calendar.add(Calendar.DAY_OF_MONTH,2);


        textView = new TextView(getContext());
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,11);
        textView.setText("后天");
        textView.setTextColor(getResources().getColor(R.color.sub_title_color));
        LayoutParams params2 = new LayoutParams(widthEach, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLlHeatDay.addView(textView,params2);
        textView = new TextView(getContext());
        LayoutParams params3 = new LayoutParams(widthEach, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLlHeatDay.addView(textView,params3);
    }

}
