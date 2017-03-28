package com.dalong.library.animator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.dalong.library.R;
import com.dalong.library.view.LoopRotarySwitchView;

/**
 * Created by INF on 2017/3/23.
 */

public class MyAnimatorUtil {
    public static ValueAnimator scaleLLMatchParentByTag(final View view){
        ValueAnimator valueAnimator = ValueAnimator.ofObject(
                new LayoutParamsTypeEvaluator(),
                view.getTag(R.id.orig_tag),
                view.getTag(R.id.match_tag));

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                SimpleRECT newSize =(SimpleRECT) animation.getAnimatedValue();
                RelativeLayout.LayoutParams old = (RelativeLayout.LayoutParams) view.getLayoutParams();
                old.width = newSize.width;
                old.height = newSize.height;
                view.setLayoutParams(old);
                //pareant.invalidate();
            }
        });
        valueAnimator.setDuration(1000);
        //valueAnimator.setRepeatCount(1);
       // valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.start();
        return valueAnimator;
    }
    public static ValueAnimator scaleLLOrigLayout(final View view){
        ValueAnimator valueAnimator = ValueAnimator.ofObject(
                new LayoutParamsTypeEvaluator(),
                view.getTag(R.id.match_tag),
                view.getTag(R.id.orig_tag));

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                SimpleRECT newSize =(SimpleRECT) animation.getAnimatedValue();
                RelativeLayout.LayoutParams old = (RelativeLayout.LayoutParams) view.getLayoutParams();
                old.width = newSize.width;
                old.height = newSize.height;
                view.setLayoutParams(old);
                //pareant.invalidate();
            }
        });
        valueAnimator.setDuration(1000);
        //valueAnimator.setRepeatCount(1);
        // valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.start();
        return valueAnimator;

    }
}
