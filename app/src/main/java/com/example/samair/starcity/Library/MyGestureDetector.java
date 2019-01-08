package com.example.samair.starcity.Library;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;
import android.R.anim;

import com.example.samair.starcity.R;

public class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    private ViewFlipper flipper;
    private Context context;

    public MyGestureDetector(ViewFlipper flipper, Context context){
        this.flipper = flipper;
        this.context = context;
    }

    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        System.out.println(" in onFling() :: ");
        if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
            return false;
        if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            flipper.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_from_right));
            flipper.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_to_left));
            flipper.showNext();
        } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            flipper.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_from_left));
            flipper.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_to_right));
            flipper.showPrevious();
        }
        return super.onFling(e1, e2, velocityX, velocityY);
    }
}