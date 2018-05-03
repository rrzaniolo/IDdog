package rrzaniolo.iddog.base.configurations;

/*
 * Created by rrzaniolo on 01/05/18.
 * Copyright © 2018 rrzaniolo. All rights reserved.
 */

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.design.widget.TabLayout;

import rrzaniolo.iddog.BR;

/**
 * Class for configuring a TabLayout using DataBiding.
 * */
public class TabLayoutConfiguration extends BaseObservable {

    //region --- Variables ---
    private TabLayout.OnTabSelectedListener onTabSelectedListener;
    //endregion

    //region --- Getters and Setters ---
    @Bindable
    public TabLayout.OnTabSelectedListener getOnTabSelectedListener() {
        return onTabSelectedListener;
    }

    public void setOnTabSelectedListener(TabLayout.OnTabSelectedListener onTabSelectedListener) {
        this.onTabSelectedListener = onTabSelectedListener;
        notifyPropertyChanged(BR.onTabSelectedListener);
    }
    //endregion
}

