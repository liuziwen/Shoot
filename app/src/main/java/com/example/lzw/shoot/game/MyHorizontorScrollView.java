package com.example.lzw.shoot.game;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by lzw on 2015/4/28.
 */
public class MyHorizontorScrollView  extends HorizontalScrollView
    {
        /**
         * 屏幕宽度
         */
        private int mScreenWidth;
        /**
         * dp
         */
        private int mMenuRightPadding ;
        /**
         * 菜单的宽度
         */
        static int LeftWidth;
        private int mHalfMenuWidth;

        private boolean once;

        public MyHorizontorScrollView (Context context, AttributeSet attrs)
        {
            super(context, attrs);
            WindowManager wm= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            mScreenWidth = wm.getDefaultDisplay().getWidth();
            mMenuRightPadding=mScreenWidth/4;
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
        {
            /**
             * 显示的设置一个宽度
             */
            if (!once)
            {
                LinearLayout wrapper = (LinearLayout) getChildAt(0);
                ViewGroup menu = (ViewGroup) wrapper.getChildAt(0);
                ViewGroup content = (ViewGroup) wrapper.getChildAt(1);
                // dp to px
//                mMenuRightPadding = (int) TypedValue.applyDimension(
//                        TypedValue.COMPLEX_UNIT_DIP, mMenuRightPadding, content
//                                .getResources().getDisplayMetrics());

                LeftWidth = mScreenWidth - mMenuRightPadding;
                mHalfMenuWidth = LeftWidth / 2;
                menu.getLayoutParams().width = LeftWidth;
                content.getLayoutParams().width = 3*mScreenWidth/4;

            }
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        }

        @Override
        protected void onLayout(boolean changed, int l, int t, int r, int b)
        {
            super.onLayout(changed, l, t, r, b);
            if (changed)
            {
                // 将菜单隐藏
                this.scrollTo(LeftWidth, 0);
                once = true;
            }
        }

       // @Override
//        public boolean onTouchEvent(MotionEvent ev)
//        {
//            int action = ev.getAction();
//            switch (action)
//            {
//                // Up时，进行判断，如果显示区域大于菜单宽度一半则完全显示，否则隐藏
//                case MotionEvent.ACTION_UP:
//                    int scrollX = getScrollX();
//                    if (scrollX > mHalfMenuWidth)
//                        this.smoothScrollTo(LeftWidth, 0);
//                    else
//                        this.smoothScrollTo(0, 0);
//                    return true;
//            }
//            return super.onTouchEvent(ev);
//        }

    }

