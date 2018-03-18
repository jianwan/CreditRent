package com.example.wanjian.creditrent.moudles.homepage.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by wanjian on 2017/12/1.
 */

public class MyScrollView extends ScrollView {

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(changed){
            LinearLayout v = (LinearLayout) getChildAt(0);
            if(v != null){
                for(int i=0;i<v.getChildCount();i++){
                    if(v.getChildAt(i).getTag() != null && ((String)v.getChildAt(i).getTag()).equals("aaa")){
                        View view = v.getChildAt(i);
                        break;
                    }
                }
            }
        }
    }

    //    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//
//        switch (ev.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                //必须返回false，否则子控件永远无法拿到焦点
//                return false;
//            case MotionEvent.ACTION_MOVE:
//                if() {
//                    return false;
//                } else {
//                    return super.onInterceptTouchEvent(ev);
//                }
//            case MotionEvent.ACTION_UP:
//                //必须返回false,否则子控件永远无法拿到焦点
//                return false;
//            default:
//                return super.onInterceptTouchEvent(ev);
//        }
//
//    }


}
