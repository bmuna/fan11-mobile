package com.fan.core.custom_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * Created by mohit.soni @ 01-Oct-19.
 */

public class CustomViewPager extends ViewPager {


    private boolean scrollDisable = false;
    int child_id;
    private boolean status = false;

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        /*if(child_id > 0){
            View scroll = findViewById(child_id);
            if(scroll!=null){
                Rect rect = new Rect();
                scroll.getHitRect(rect);
                if (rect.contains((int) event.getX(), (int) event.getY())) {
                    return false;
                }
            }
        }
        if (scrollDisable) {
            return false;
        } else {
            return super.onInterceptTouchEvent(event);
        }*/
        return false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE: {
                status = false;
                break;
            }
            case MotionEvent.ACTION_UP: {
                status = false;
                break;
            }
            case MotionEvent.ACTION_DOWN: {
                status = false;
                break;
            }
            case MotionEvent.ACTION_CANCEL:
                status = false;
                break;
        }
        return status;
    }

    public void setChild_id(int child_id) {
        this.child_id = child_id;
    }

    public void disableScroll() {
        scrollDisable = true;
    }

    public void enableScroll() {
        scrollDisable = false;
    }


}
