package com.example.inf.smarthome3.animator;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by INF on 2017/3/23.
 */

public class MyAnimatorUtil {
    public static void scaleLLMatchParent(final LinearLayout ll,RelativeLayout.LayoutParams pareantParams){
        Log.i("aaa", "scaleLLMatchParent: "+ll.getLayoutParams() + pareantParams);
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new LinearLayoutParamsTypeEvaluator(),ll.getLayoutParams(),pareantParams);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ll.setLayoutParams((RelativeLayout.LayoutParams)animation.getAnimatedValue());
            }
        });
        valueAnimator.setDuration(2000);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.setRepeatCount(1);
        valueAnimator.start();
    }
}
