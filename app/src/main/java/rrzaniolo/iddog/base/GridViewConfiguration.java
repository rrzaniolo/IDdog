package rrzaniolo.iddog.base;

/*
 * Created by rrzaniolo on 02/05/18.
 * Copyright © 2018 rrzaniolo. All rights reserved.
 */

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.widget.GridView;

import rrzaniolo.iddog.BR;

/**
 * Class for configuring a GridView using DataBiding.
 * */
public class GridViewConfiguration extends BaseObservable{

    //region --- Variables ---
    private CustomGridViewAdapter adapter;
    private GridView.OnItemClickListener clickListener;
    //endregion

    //region --- Getters and Setters ---
    @Bindable
    public CustomGridViewAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(CustomGridViewAdapter adapter) {
        this.adapter = adapter;
        notifyPropertyChanged(BR.adapter);
    }

    @Bindable
    public GridView.OnItemClickListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(GridView.OnItemClickListener clickListener) {
        this.clickListener = clickListener;
        notifyPropertyChanged(BR.clickListener);
    }
    //endregion
}
