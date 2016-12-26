package com.example.qq.util.animation_;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

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

    public void setScaleAnimation() {
        scaleAnimation = new ScaleAnimation(
                0f, 1.0f,
                0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        );
        animation_t(scaleAnimation);
    }

    public void setAlphaAnimation() {
        alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        animation_t(alphaAnimation);
    }

    public void setRotateAnimation() {
        rotateAnimation = new RotateAnimation(
                0, -90,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f
        );
        rotateAnimation.setDuration(400);
        //动画完了后停止在当前位置
        rotateAnimation.setFillAfter(true);
        view.startAnimation(rotateAnimation);
    }

    public void setRotateAnimationOpposite() {
        rotateAnimation = new RotateAnimation(
                0, 180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f
        );
        rotateAnimation.setDuration(400);
        //动画完了后停止在当前位置
        rotateAnimation.setFillAfter(true);
        view.startAnimation(rotateAnimation);
    }

    public void setReserveRotateAnimationOpposite() {
        rotateAnimation = new RotateAnimation(
                180, 0,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f
        );
        rotateAnimation.setDuration(400);
        //动画完了后停止在当前位置
        rotateAnimation.setFillAfter(true);
        view.startAnimation(rotateAnimation);
    }

    public void animation_t(Animation animation) {
        animation.setDuration(800);
        //动画完了后停止在当前位置
        animation.setFillAfter(true);
        view.startAnimation(animation);
    }

}
