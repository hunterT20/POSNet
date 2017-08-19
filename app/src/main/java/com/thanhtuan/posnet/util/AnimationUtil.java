package com.thanhtuan.posnet.util;

import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.thanhtuan.posnet.R;

/**
 * Class hiệu ứng trong app
 */
public class AnimationUtil {
    public static void SlideUP(View view, Context context)
    {
        view.startAnimation(AnimationUtils.loadAnimation(context,
                R.anim.slide_up));
    }

    public static void SlideDown(View view,Context context)
    {
        view.startAnimation(AnimationUtils.loadAnimation(context,
                R.anim.slide_down));
    }
}
