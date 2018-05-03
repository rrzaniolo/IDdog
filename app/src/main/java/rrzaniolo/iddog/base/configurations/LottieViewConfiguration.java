package rrzaniolo.iddog.base.configurations;

/*
 * Created by rrzaniolo on 03/05/18.
 * Copyright © 2018 rrzaniolo. All rights reserved.
 */

import android.animation.Animator;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.RawRes;

import rrzaniolo.iddog.BR;

/**
 * Class for configuring a LottieView using DataBinding.
 * */
@SuppressWarnings("unused")
public class LottieViewConfiguration extends BaseObservable {

    //region --- VARIABLES ---
    private @RawRes
    int animation;
    private boolean loop;
    private float scale = -1f;
    private float speed = -1f;
    private long startDelay = 0;
    private Animator.AnimatorListener animatorListener;
    //endregion

    //region --- GETTERS & SETTERS ---
    @Bindable
    public int getAnimation() {
        return animation;
    }

    public void setAnimation(int animation) {
        this.animation = animation;
        notifyPropertyChanged(BR.animation);
    }

    @Bindable
    public boolean isLoop() {
        return loop;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
        notifyPropertyChanged(BR.loop);
    }

    @Bindable
    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
        notifyPropertyChanged(BR.scale);
    }

    @Bindable
    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
        notifyPropertyChanged(BR.speed);
    }

    @Bindable
    public long getStartDelay() {
        return startDelay;
    }

    public void setStartDelay(long startDelay) {
        this.startDelay = startDelay;
        notifyPropertyChanged(BR.startDelay);
    }

    @Bindable
    public Animator.AnimatorListener getAnimatorListener() {
        return animatorListener;
    }

    public void setAnimatorListener(Animator.AnimatorListener animatorListener) {
        this.animatorListener = animatorListener;
        notifyPropertyChanged(BR.animatorListener);
    }
    //endregion
}

