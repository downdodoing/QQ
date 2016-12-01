package com.example.qq.util.animation_;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * Created by 小凳子 on 2016/11/24.
 */

public class Animation_ {
    private View view;
    //移动动画
    private TranslateAnimation translateAnimation;
    //尺寸缩放动画
    private ScaleAnimation scaleAnimation;
    //淡入淡出动画
    private AlphaAnimation alphaAnimation;
    //旋转动画
    private RotateAnimation rotateAnimation;

    public Animation_(View view) {
        this.view = view;
    }

    public void setTranslateAnimationranslate(int fromXDelta, int toXDelta, int fromYDelta, int toYDelta) {
        translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, fromXDelta,
                Animation.RELATIVE_TO_SELF, toXDelta,
                Animation.RELATIVE_TO_SELF, fromYDelta,
                Animation.RELATIVE_TO_SELF, toYDelta
        );
        animation_t(translateAnimation);
    }

    /*
    * 默认移动位移
    * */
    public void setTranslateAnimationranslate() {
        translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0f
        );
        animation_t(translateAnimation);
    }

    /*
    * fromXScale:X坐标开始尺寸
    *toXScale：X坐标结束尺寸
    *pivotX：属性为动画相对于物件的X坐标的开始位置
    * */
    public void setScaleAnimation(float fromXScale, float toXScale, float fromYScale, float toYScale, float pivotX, float pivotY) {
        scaleAnimation = new ScaleAnimation(
                fromXScale, toXScale,
                fromYScale, toYScale,
                Animation.RELATIVE_TO_SELF, pivotX, Animation.RELATIVE_TO_SELF, pivotY
        );
        animation_t(scaleAnimation);
    }

    public void setScaleAnimation() {
        scaleAnimation = new ScaleAnimation(
                0f, 1.0f,
                0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        );
        animation_t(scaleAnimation);
    }

    public void setAlphaAnimation(float fromAlpha, float toAlpha) {
        alphaAnimation = new AlphaAnimation(fromAlpha, toAlpha);
        animation_t(alphaAnimation);
    }

    public void setAlphaAnimation() {
        alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        animation_t(alphaAnimation);
    }

    public void setRotateAnimation(float fromDegrees, float toDegrees, float pivotX, float pivotY) {
        rotateAnimation = new RotateAnimation(
                fromDegrees, toDegrees,
                RotateAnimation.RELATIVE_TO_SELF, pivotX, RotateAnimation.RELATIVE_TO_SELF, pivotY
        );
        animation_t(rotateAnimation);
    }

    public void setRotateAnimation() {
        rotateAnimation = new RotateAnimation(
                0, 360,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f
        );
        animation_t(rotateAnimation);
    }

    public void animation_t(Animation animation) {
        animation.setDuration(800);
        //动画完了后停止在当前位置
        animation.setFillAfter(true);
        view.startAnimation(animation);
    }

}
