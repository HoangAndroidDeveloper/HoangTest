package com.example.appshopping;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class Animation_Cart {
    private static final int Duration = 1000;
    private static boolean isAnimationStart;
    public static void translateAnimation (final ImageView viewAnimation, final View startView, final View endView
            , final Animation.AnimationListener listener)
    {
        startView.setDrawingCacheEnabled(true);
        ImageView imageView = (ImageView) startView;
        Drawable drawable = imageView.getDrawable();
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();
        startView.setDrawingCacheEnabled(true);
        viewAnimation.setImageBitmap(bitmap);
        float startViewWidthCenter = startView.getWidth()/2;
        float startViewHeightCenter = startView.getHeight()/2;

        float endViewWidthCenter = endView.getWidth() * 0.75f;
        float endViewHeightCenter = endView.getHeight() * 2f;
        final int [] startPos = new int[2];
        startView.getLocationOnScreen(startPos);
        final  int [] endPos =  new int[2];
        endView.getLocationOnScreen(endPos);
        float formX = startPos [0];
        float toX = startPos[0] + endViewWidthCenter - startViewWidthCenter;
        float formY = startPos [1] - startViewHeightCenter;
        float toY = endPos[1] - endViewHeightCenter + startViewHeightCenter;
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setInterpolator(new AccelerateInterpolator());
        int animationDuration = 200;
        ScaleAnimation startScaleAnimation = new ScaleAnimation(1.0f, 1.5f,1.0f
                ,1.5f,startViewWidthCenter, startViewHeightCenter);
        startScaleAnimation.setDuration(animationDuration);
        TranslateAnimation translateAnimation = new TranslateAnimation(formX, toX, formY, toY);
        translateAnimation.setStartOffset(animationDuration);
        translateAnimation.setDuration(Duration);
        ScaleAnimation translateScaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f
                , 0.0f, toX, toY);
        translateAnimation.setStartOffset(animationDuration);
        translateAnimation.setDuration(Duration);
        animationSet.addAnimation(startScaleAnimation);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(translateScaleAnimation);
        if(isAnimationStart)
        {
            viewAnimation.clearAnimation();
            if(listener != null)
            {
                listener.onAnimationEnd(null);
            }
        }
       viewAnimation.startAnimation(animationSet);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isAnimationStart = true;
                viewAnimation.setVisibility(View.VISIBLE);
                startView.setVisibility(View.GONE);
                if(listener != null)
                {
                    listener.onAnimationStart(animation);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                 viewAnimation.setVisibility(View.GONE);
                 startView.setVisibility(View.VISIBLE);
                 if(listener != null)
                  listener.onAnimationEnd(animation);
                 isAnimationStart = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                 if(listener != null)
                 {
                     listener.onAnimationRepeat(animation);
                 }
            }
        });

    }

}
