package rrzaniolo.iddog.base.configurations;

/*
 * Created by rrzaniolo on 01/05/18.
 * Copyright © 2018 rrzaniolo. All rights reserved.
 */

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import rrzaniolo.iddog.BR;

/**
 * Class to configure a ViewPager using DataBinding.
 * */
@SuppressWarnings("unused")
public class ViewPagerConfiguration extends BaseObservable {

    //region --- Variables ---
    private FragmentStatePagerAdapter adapter;
    private ViewPager.PageTransformer pageTransformer;
    private ViewPager.OnPageChangeListener onPageChangeListener;
    //endregion

    //region --- Getters and Setters  ---
    @Bindable
    public FragmentStatePagerAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(FragmentStatePagerAdapter adapter) {
        this.adapter = adapter;
        notifyPropertyChanged(BR.adapter);
    }

    @Bindable
    public ViewPager.PageTransformer getPageTransformer() {
        return pageTransformer;
    }

    public void setPageTransformer(ViewPager.PageTransformer pageTransformer) {
        this.pageTransformer = pageTransformer;
        notifyPropertyChanged(BR.pageTransformer);
    }

    @Bindable
    public ViewPager.OnPageChangeListener getOnPageChangeListener() {
        return onPageChangeListener;
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
        notifyPropertyChanged(BR.onPageChangeListener);
    }
    //endregion
}
