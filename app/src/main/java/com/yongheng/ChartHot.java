package com.yongheng;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by melon on 2017/6/17.
 */

public class ChartHot extends View {

    private Paint paint1;

    private Paint paint2;

    private Paint paint3;

    private Paint paint4;

    private Paint paintCBig;
    private Paint paintCSmall;
    private float radiusCBig;
    private float radiusCSmall;

    private int widthScreen;
    private int heightScreen;


    int countH = 15;
    int realCountH = countH+1;

    int level = 0;


    List<Integer> chartMainColor = new ArrayList<Integer>(){
        {
            add(R.color.heat_chart_blue_main);
            add(R.color.heat_chart_green_main);
            add(R.color.heat_chart_red_main);
        }
    };

    List<Integer> chartLineColor = new ArrayList<Integer>(){
        {
            add(R.color.heat_chart_blue_line);
            add(R.color.heat_chart_green_line);
            add(R.color.heat_chart_red_line);
        }
    };

    List<Integer> chartCircularColor = new ArrayList<Integer>(){
        {
            add(R.color.heat_chart_blue_circular);
            add(R.color.heat_chart_green_circular);
            add(R.color.heat_chart_red_circular);
        }
    };

    List<Float> indexs = new ArrayList<Float>(){
        {
            add(0.50f);
            add(0.55f);
            add(0.56f);
            add(0.57f);

            add(0.60f);
            add(0.65f);
            add(0.68f);
            add(0.70f);

            add(0.85f);
            add(0.84f);
            add(0.82f);
            add(0.80f);

            add(0.72f);
            add(0.68f);
            add(0.55f);
            add(0.50f);
        }
    };

    public ChartHot(Context context) {
        super(context);
        init();
    }

    public ChartHot(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){


        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        widthScreen = metrics.widthPixels;
        heightScreen = getContext().getResources().getDimensionPixelOffset(R.dimen.heat_chart_height);
        paint1 = new Paint();
        paint1.setColor(getContext().getResources().getColor(R.color.temp_color1));

        paint2 = new Paint();
        paint2.setColor(getContext().getResources().getColor(chartMainColor.get(level)));

        paint3 = new Paint();
        paint3.setColor(getContext().getResources().getColor(R.color.temp_color3));

        paint4 = new Paint();
        paint4.setAntiAlias(true);
        paint4.setStrokeWidth(getContext().getResources().getDimension(R.dimen.margin_2));
        paint4.setColor(getContext().getResources().getColor(chartLineColor.get(level)));

        paintCBig = new Paint();
        paintCBig.setAntiAlias(true);
        paintCBig.setColor(getResources().getColor(R.color.white));
        radiusCBig = getResources().getDimensionPixelOffset(R.dimen.margin_8);

        paintCSmall = new Paint();
        paintCSmall.setAntiAlias(true);
        paintCSmall.setColor(getResources().getColor(chartCircularColor.get(level)));
        radiusCSmall = getResources().getDimensionPixelOffset(R.dimen.margin_6);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int countRectangle = realCountH%2==0?realCountH/2:realCountH/2+1;
        int widthRectangle = widthScreen/realCountH;

        RectF rect;
        Path path = new Path();

        for (int i = 0;i < countRectangle;i++){

            int left = i*2*widthRectangle;
            int right = left + widthRectangle;
            rect = new RectF(left, 0, right, heightScreen);
            canvas.drawRect(rect,paint3);


        }

        for (int i = 0;i < countH;i++){
            if (i == 0){
                path.moveTo(0,indexs.get(0)*heightScreen);
            }else{
                path.lineTo(i*widthRectangle,(1-indexs.get(i))*heightScreen);
            }
        }
        path.lineTo((countH-1)*widthRectangle,heightScreen);
        path.lineTo(0,heightScreen);
        path.lineTo(0,indexs.get(0)*heightScreen);
        canvas.drawPath(path,paint2);

        for (int i = 0;i < countRectangle;i++){
            int left = i*2*widthRectangle;
            int right = left + widthRectangle;
            rect = new RectF(left, 0, right, heightScreen);
            canvas.drawRect(rect,paint1);
        }

        float lineStartX = 0;
        float lineStartY = (1-indexs.get(0))*heightScreen;
        for (int i = 1;i< countH;i++){
            canvas.drawLine(lineStartX,lineStartY,i*widthRectangle,(1-indexs.get(i))*heightScreen,paint4);
            lineStartX = i*widthRectangle;
            lineStartY = (1-indexs.get(i))*heightScreen;
        }

        float circularX = (countH-1)*widthRectangle;
        float circularY = (1-indexs.get(countH-1))*heightScreen;
        canvas.drawCircle(circularX, circularY, radiusCBig, paintCBig);
        canvas.drawCircle(circularX, circularY, radiusCSmall, paintCSmall);
    }
}
