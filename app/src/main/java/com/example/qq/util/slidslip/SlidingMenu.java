package com.example.qq.util.slidslip;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.example.qq.R;
import com.nineoldandroids.view.ViewHelper;

public class SlidingMenu extends HorizontalScrollView {
    //屏幕宽度
    private int mScreenWidth;
    //菜单栏宽度
    private int mMenuWidth;
    private int mHalfMenuWidth;

    private ViewGroup mMenu;
    private ViewGroup mContent;

    //用于判断菜单栏是否打开
    private boolean isOpen;
    //用于判断是否是第一次加载该view
    private boolean once = true;

    //充斥整个屏幕的view离右边框的距离
    private int mMenuRightPadding;

    public SlidingMenu(Context context) {
        this(context, null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScreenWidth = ScreenUtil.getScreenWidth(context);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SlidingMenu, defStyleAttr, 0);
        int count = typedArray.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = typedArray.getIndex(i);
            if (attr == R.styleable.SlidingMenu_rightPadding) {
                //默认为50sp
                mMenuRightPadding = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50f, getResources().getDisplayMetrics()));
                break;
            }
            typedArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //设置子View的初始宽度
        if (once) {
            LinearLayout wrapper = (LinearLayout) getChildAt(0);
            mMenu = (ViewGroup) wrapper.getChildAt(0);
            mContent = (ViewGroup) wrapper.getChildAt(1);

            mMenuWidth = mScreenWidth - mMenuRightPadding;
            mHalfMenuWidth = mMenuWidth / 2;
            mMenu.getLayoutParams().width = mMenuWidth;
            mContent.getLayoutParams().width = mScreenWidth;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            this.scrollTo(mMenuWidth, 0);
            once = false;
        }
    }

    //打开菜单
    public void openMenu() {
        if (isOpen) {
            return;
        }
        this.smoothScrollTo(0, 0);
        isOpen = true;
    }

    //关闭菜单
    public void closeMenu() {
        if (isOpen) {
            this.smoothScrollTo(mMenuWidth, 0);
            isOpen = false;
        }
    }

    //切换菜单状态按钮
    public void toggle() {
        if (isOpen) {
            closeMenu();
        } else {
            openMenu();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                if (scrollX > mHalfMenuWidth) {
                    this.smoothScrollTo(mMenuWidth, 0);
                    isOpen = false;
                } else {
                    this.smoothScrollTo(0, 0);
                    isOpen = true;
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        float scale = l * 1.0f / mMenuWidth;
        float leftScale = 1 - 0.3f * scale;
        float rightScale = 0.8f + scale * 0.2f;

//        ViewHelper.setScaleX(mMenu, leftScale);//缩放
//        ViewHelper.setScaleY(mMenu, leftScale);
        ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));//透明度
        ViewHelper.setTranslationX(mMenu, mMenuWidth * scale * 0.7f);//位移

//        ViewHelper.setScaleX(mContent, rightScale);
//        ViewHelper.setScaleY(mContent, rightScale);
    }
}
