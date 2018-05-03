package rrzaniolo.iddog.base.configurations;

/*
 * Created by rrzaniolo on 02/05/18.
 * Copyright © 2018 rrzaniolo. All rights reserved.
 */

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import rrzaniolo.iddog.BR;
import rrzaniolo.iddog.base.adapters.FeedRecyclerViewAdapter;

/**
 * Class for configuring a GridView using DataBiding.
 * */
public class RecyclerViewConfiguration extends BaseObservable{

    //region --- Variables ---
    private FeedRecyclerViewAdapter adapter;
    //endregion

    //region --- Getters and Setters ---
    @Bindable
    public FeedRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(FeedRecyclerViewAdapter adapter) {
        this.adapter = adapter;
        notifyPropertyChanged(BR.adapter);
    }
    //endregion
}
