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

    //region --- CustomTab ---
    public static class CustomTab {

        //region --- Variables ---
        private String title;
        private boolean enable;
        private int layout;
        //endregion

        //region --- Constructors ---
        public CustomTab(String title, boolean enable, int layout) {
            this.title = title;
            this.enable = enable;
            this.layout = layout;
        }
        //endregion

        //region --- Getters and Setters ---
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }

        public int getLayout() {
            return layout;
        }

        public void setLayout(int layout) {
            this.layout = layout;
        }
        //endregion
    }
    //endregion
}

