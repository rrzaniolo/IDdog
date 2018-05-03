package rrzaniolo.iddog.home;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;

import rrzaniolo.iddog.BR;
import rrzaniolo.iddog.LiveEvents.OnTabSelected;
import rrzaniolo.iddog.base.adapters.CustomPagerAdapter;
import rrzaniolo.iddog.base.configurations.TabLayoutConfiguration;
import rrzaniolo.iddog.base.configurations.ViewPagerConfiguration;
import rrzaniolo.iddog.data.TabSelectionParameter;
import rrzaniolo.iddog.home.feed.FeedFragment;
import rrzaniolo.iddog.utils.Constants;

/*
 * Created by Rodrigo Rodrigues Zaniolo on 4/28/2018.
 * All rights reserved.
 */

/**
 * The ViewModel for the HomeActivity.
 * */
public class HomeViewModel extends AndroidViewModel implements TabLayout.OnTabSelectedListener{

    //region --- Variables ---
    private final OnTabSelected onTabSelected = new OnTabSelected();

    private final ViewPagerConfiguration vpConfiguration;
    private final TabLayoutConfiguration tlConfiguration;

    private CustomPagerAdapter customPagerAdapter;

    private ObservableField<Integer> startPosition = new ObservableField<>(0);
    //endregion

    //region --- Getters and Setters ---
    public OnTabSelected getOnTabSelected() {
        return onTabSelected;
    }

//    @Bindable
    public ViewPagerConfiguration getVpConfiguration() {
        return vpConfiguration;
    }

    public TabLayoutConfiguration getTlConfiguration() {
        return tlConfiguration;
    }

    private CustomPagerAdapter getCustomPagerAdapter() {
        return customPagerAdapter;
    }

    private void setCustomPagerAdapter(CustomPagerAdapter customPagerAdapter) {
        this.customPagerAdapter = customPagerAdapter;
    }

    public ObservableField<Integer> getStartPosition() {
        return startPosition;
    }
    //endregion

    //region --- Constructors ---
    public HomeViewModel(@NonNull Application application,
                         ViewPagerConfiguration vpConfiguration, TabLayoutConfiguration tlConfiguration) {
        super(application);

        /* Init ViewModel. */
        this.vpConfiguration = vpConfiguration;
        this.tlConfiguration = tlConfiguration;
    }
    //endregion

    //region --- Private Methods ---
    private void configureCustomAdapter(){

        getCustomPagerAdapter().addFragment(
                newFeed(Constants.CATEGORY_HOUND),
                Constants.CATEGORY_HOUND
        );
        getCustomPagerAdapter().addFragment(
                newFeed(Constants.CATEGORY_HUSKY),
                Constants.CATEGORY_HUSKY
        );
        getCustomPagerAdapter().addFragment(
                newFeed(Constants.CATEGORY_LABRADOR),
                Constants.CATEGORY_LABRADOR
        );
        getCustomPagerAdapter().addFragment(
                newFeed(Constants.CATEGORY_PUG),
                Constants.CATEGORY_PUG
        );
    }

    private FeedFragment newFeed(String category){
        FeedFragment fragment = new FeedFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_CATEGORY_BREED, category);
        fragment.setArguments(bundle);

        return fragment;
    }
    //endregion

    //region Public Methods ---
    public void setUpViewModel(FragmentManager fragmentManager){
        setCustomPagerAdapter(new CustomPagerAdapter(fragmentManager));
        configureCustomAdapter();

        getVpConfiguration().setAdapter(getCustomPagerAdapter());
        vpConfiguration.notifyPropertyChanged(BR.adapter);

        getTlConfiguration().setOnTabSelectedListener(HomeViewModel.this);

    }
    //endregion

    //region --- OnTabSelectedListener ---
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        getOnTabSelected().setValue(new TabSelectionParameter(tab, true));
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        getOnTabSelected().setValue(new TabSelectionParameter(tab, false));
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {  }
    //endregion
}
