package rrzaniolo.iddog.home.feed;

/*
 * Created by rrzaniolo on 02/05/18.
 * Copyright © 2018 rrzaniolo. All rights reserved.
 */

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rrzaniolo.iddog.R;
import rrzaniolo.iddog.ViewModelFactory;
import rrzaniolo.iddog.databinding.FragmentFeedBinding;
import rrzaniolo.iddog.utils.Constants;

import static rrzaniolo.iddog.utils.Preconditions.checkNotNull;

public class FeedFragment extends Fragment{

    //region --- Constants ---
    private static String TAG ="FeedFragment";
    //endregion

    //region --- Variables ---
    private String breed;

    private static FeedFragment instance;
    private FragmentFeedBinding binding;
    private FeedViewModel viewModel;
    //endregion

    //region --- Getters and Setters ---
    public static FeedFragment newInstance(String breed) {
        if (instance == null){
            instance = new FeedFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constants.BUNDLE_CATEGORY_BREED, breed);
            instance.setArguments(bundle);
        }

        return instance;
    }

    private FragmentFeedBinding getBinding() {
        return binding;
    }

    private void setBinding(LayoutInflater inflater, ViewGroup container) {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feed, container, false);
    }

    private FeedViewModel getViewModel() {
        return viewModel;
    }

    private void setViewModel() {
        this.viewModel = ViewModelFactory.getInstance(checkNotNull(getActivity()).getApplication()).create(FeedViewModel.class);
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getBreed() {
        return breed;
    }
    //endregion

    //region --- Constructors ---


    //region --- Life Cycle ---
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        try {
            setBreed(checkNotNull(getArguments()).getString(Constants.BUNDLE_CATEGORY_BREED));
            setViewModel();
            getViewModel().configureViewModel(getBreed());
            setUpBinding(inflater, container);

        }catch (NullPointerException e){
            Log.e(TAG, e.getLocalizedMessage());
        }

        return getBinding().getRoot();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }
    //endregion

    //region --- Private Methods ---
    private void setUpBinding(LayoutInflater inflater, ViewGroup container){
        setBinding(inflater, container);
        getBinding().setViewModel(getViewModel());
    }
}
