package com.dalong.library.animator;

import android.animation.TypeEvaluator;
import android.graphics.Rect;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by INF on 2017/3/23.
 */

public class LayoutParamsTypeEvaluator implements TypeEvaluator<SimpleRECT> {
    @Override
    public SimpleRECT evaluate(float fraction, SimpleRECT startValue, SimpleRECT endValue) {
        SimpleRECT newRect = new SimpleRECT(
                (int)(startValue.width + fraction * (endValue.width - startValue.width )),
                (int)(startValue.height + fraction * (endValue.height - startValue.height ))

        ) ;
        return newRect;

    }
}
