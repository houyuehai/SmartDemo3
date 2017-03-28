package com.example.inf.smarthome3.animator;

import android.animation.TypeEvaluator;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by INF on 2017/3/23.
 */

public class LinearLayoutParamsTypeEvaluator implements TypeEvaluator<RelativeLayout.LayoutParams> {
    @Override
    public RelativeLayout.LayoutParams evaluate(float fraction,RelativeLayout.LayoutParams startValue,RelativeLayout.LayoutParams endValue) {
        Log.i("aaa", "evaluate: "+startValue);
        float width = startValue.width + fraction * (endValue.width - startValue.width );
        float height = startValue.height + fraction * (endValue.height - startValue.height );
        return new RelativeLayout.LayoutParams((int)width,(int)height);
    }
}
