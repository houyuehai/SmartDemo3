package com.example.inf.smarthome3.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.inf.smarthome3.R;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_UP;

/**
 * Created by inf on 2017/3/21.
 */
public class ProgressRegulator extends View {

    private Canvas canvas;

    private int darkColor = Color.GRAY;
    private int lightColor = Color.RED;

    private int roundColor = darkColor;
    private int roundProgressColor = lightColor;
    private int titleTextColor = lightColor;
    private int subTitleTextColor = darkColor;
    private int sub2TitleTextColor = darkColor;

    private int roundWidth = 10;
    private int startAngle = 270 ;
    private int lightTextSize = 60;
    private int darkTextSize = 100;

    private int padding = 0 ;
    private int progress = 0;
    private int max = 100;

    private String title = "open";
    private String subtitle = "close";

    boolean progressIncreaseNow;
    boolean progressRefreshAuto;
    public interface ProgressActionListener{
        void onProgressChange(int progress);
        void onProgressActionDown();
        void onProgressActionUp();
    }

    private ProgressActionListener progressActionListener;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ProgressRegulator.this.setProgress(msg.arg1);
        }
    };

    private Paint paint ;
    public ProgressRegulator(Context context) {
        super(context);
    }

    public ProgressRegulator(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ProgressRegulator);
        darkColor = array.getColor(R.styleable.ProgressRegulator_darkColor,darkColor);
        lightColor = array.getColor(R.styleable.ProgressRegulator_lightColor,lightColor);
        roundColor = array.getColor(R.styleable.ProgressRegulator_roundColor,darkColor);
        roundProgressColor = array.getColor(R.styleable.ProgressRegulator_roundProgressColor,lightColor);
        subTitleTextColor = array.getColor(R.styleable.ProgressRegulator_subTitleTextColor,darkColor);
        sub2TitleTextColor = array.getColor(R.styleable.ProgressRegulator_sub2TitleTextColor,darkColor);
        titleTextColor  = array.getColor(R.styleable.ProgressRegulator_title1TextColor,lightColor);

        roundWidth = array.getInt(R.styleable.ProgressRegulator_roundWidth,roundWidth);
        lightTextSize = array.getInt(R.styleable.ProgressRegulator_lightTextSize,lightTextSize);
        darkTextSize = array.getInt(R.styleable.ProgressRegulator_darkTextSize,darkTextSize);
        lightTextSize = getWidth()/5;
        darkTextSize = getWidth()/3;


        progress = array.getInt(R.styleable.ProgressRegulator_progress,progress);
        max = array.getInt(R.styleable.ProgressRegulator_max,max);
        padding = array.getInt(R.styleable.ProgressRegulator_padding,padding);
        startAngle = array.getInt(R.styleable.ProgressRegulator_startAngle,startAngle);
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec,widthMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas ;
        lightTextSize = getWidth()/15;
        darkTextSize = getWidth()/5;
        /**
         *
         * 画最外层的大圆环
        */
        int centre = getWidth()/2; //获取圆心的x坐标
        int radius = (centre - roundWidth/2 - padding); //圆环的半径
        paint.setColor(roundColor); //设置圆环的颜色
        paint.setStyle(Paint.Style.STROKE); //设置空心
        paint.setStrokeWidth(roundWidth); //设置圆环的宽度
        paint.setAntiAlias(true);  //消除锯齿
        canvas.drawCircle(centre, centre, radius, paint); //画出圆环

        /**
         *中间文字 上
         */
        paint.setStrokeWidth(0);
        paint.setColor(subTitleTextColor);
        paint.setTextSize(lightTextSize);
        paint.setTypeface(Typeface.DEFAULT_BOLD); //设置字体
        float textWidth = paint.measureText(title);   //测量字体宽度，我们需要根据字体的宽度设置在圆环中间
        canvas.drawText(title, centre - textWidth / 2, centre - centre/3 , paint); //画出进度百分比


        /**
         *中间文字 下
         */
        paint.setColor(sub2TitleTextColor);
        textWidth = paint.measureText(subtitle);   //测量字体宽度，我们需要根据字体的宽度设置在圆环中间
        canvas.drawText(subtitle, centre - textWidth / 2, centre + centre/3+lightTextSize/2, paint); //画出进度百分比


        /**
         *中间文字 中
         */
        paint.setColor(titleTextColor);
        paint.setTextSize(darkTextSize);
        paint.setTypeface(Typeface.DEFAULT_BOLD); //设置字体
        textWidth = paint.measureText(String.valueOf(progress));   //测量字体宽度，我们需要根据字体的宽度设置在圆环中间
        canvas.drawText(String.valueOf(progress), centre - textWidth / 2, centre +darkTextSize/3, paint); //画出进度百分比

        /**
         * 画圆弧 ，画圆环的进度
         */

        paint.setStrokeWidth(roundWidth); //设置圆环的宽度
        paint.setColor(roundProgressColor);  //设置进度的颜色
        RectF oval = new RectF(centre - radius, centre - radius, centre
                + radius, centre + radius);  //用于定义的圆弧的形状和大小的界限
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(oval, startAngle, 360f * (float)progress / (float)max, false, paint);  //根据进度画圆弧

    }
    public synchronized int getMax(){
        return max;
    }
    public synchronized int getProgress(){
        return progress;
    }
    public synchronized void setProgress(int progress){
        Log.i("aaa", "setProgress: "+progress);
        this.progress = progress ;
        if (progressActionListener!=null)
            progressActionListener.onProgressChange(progress);
        postInvalidate();
    }
    public synchronized void setMax(int max){
        this.max = max ;
        postInvalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case ACTION_DOWN :{
                if (progressActionListener!=null)
                    progressActionListener.onProgressActionDown();

                if (event.getY()<(getY()+getHeight()/2)){
                    progressIncreaseNow = true;
                    subTitleTextColor = lightColor;
                }else {
                    progressIncreaseNow = false;
                    sub2TitleTextColor = lightColor;
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        progressRefreshAuto = true;
                        while(progressRefreshAuto){
                            try {
                                Thread.sleep(80);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Message msg = new Message();
                            if (progressIncreaseNow){
                                msg.arg1 = progress + max/100;

                            }else {
                                msg.arg1 = progress - max/100;
                            }
                            if ( msg.arg1>=0&&msg.arg1<=max){
                                handler.sendMessage(msg);
                            }else {
                                progressRefreshAuto = false;
                            }


                        }
                    }
                }).start();

            }break;

            case ACTION_UP:{
                if (progressActionListener!=null)
                {
                    postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressActionListener.onProgressActionUp();
                        }
                    },200);
                }


                subTitleTextColor = darkColor;
                sub2TitleTextColor = darkColor;
                progressRefreshAuto = false ;

            }break;
        }
        return true;
    }

    public ProgressActionListener getProgressActionListener() {
        return progressActionListener;
    }

    public void setProgressActionListener(ProgressActionListener progressActionListener) {
        this.progressActionListener = progressActionListener;
    }
}
