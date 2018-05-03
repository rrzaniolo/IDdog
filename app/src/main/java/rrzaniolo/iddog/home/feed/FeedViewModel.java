package rrzaniolo.iddog.home.feed;

/*
 * Created by rrzaniolo on 02/05/18.
 * Copyright © 2018 rrzaniolo. All rights reserved.
 */

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rrzaniolo.iddog.LiveEvents.FeedImage;
import rrzaniolo.iddog.LiveEvents.SnackbarMessage;
import rrzaniolo.iddog.R;
import rrzaniolo.iddog.base.adapters.CustomRecyclerViewAdapter;
import rrzaniolo.iddog.base.configurations.LottieViewConfiguration;
import rrzaniolo.iddog.base.configurations.RecyclerViewConfiguration;
import rrzaniolo.iddog.network.ConsumerService;
import rrzaniolo.iddog.network.IConsumerService;
import rrzaniolo.iddog.network.entries.Feed;

import static rrzaniolo.iddog.utils.Preconditions.checkNotNull;

public class FeedViewModel extends AndroidViewModel{

    //region --- Constants ---
    private static String TAG = "FeedViewModel";

    //region --- Variables ---
    private final SnackbarMessage snackbarMessage = new SnackbarMessage();
    private final FeedImage feedImage = new FeedImage();
    private final ConsumerService consumerService;
    private final IConsumerService iConsumerService;

    private LottieViewConfiguration lottieViewConfiguration;
    private RecyclerViewConfiguration recyclerViewConfiguration;
    private CustomRecyclerViewAdapter adapter;

    private String breed = "";
    private Boolean hasFeed = false;

    private ObservableBoolean showLoading = new ObservableBoolean(false);
    //endregion

    //region --- Getters and Setters ---
    public SnackbarMessage getSnackbarMessage() {
        return snackbarMessage;
    }

    public FeedImage getFeedImage() {
        return feedImage;
    }

    private ConsumerService getConsumerService() {
        return consumerService;
    }

    private IConsumerService getIConsumerService() {
        return iConsumerService;
    }

    public LottieViewConfiguration getLottieViewConfiguration() {
        return lottieViewConfiguration;
    }

    public void setLottieViewConfiguration(LottieViewConfiguration lottieViewConfiguration) {
        this.lottieViewConfiguration = lottieViewConfiguration;
    }

    public RecyclerViewConfiguration getRecyclerViewConfiguration() {
        return recyclerViewConfiguration;
    }

    public CustomRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(CustomRecyclerViewAdapter adapter) {
        this.adapter = adapter;
    }

    private String getBreed() {
        return breed;
    }

    private void setBreed(String breed) {
        this.breed = breed;
    }

    private Boolean getHasFeed() {
        return hasFeed;
    }

    private void setHasFeed(Boolean hasFeed) {
        this.hasFeed = hasFeed;
    }

    public ObservableBoolean getShowLoading() {
        return showLoading;
    }

    public void setShowLoading(Boolean showLoading) {
        this.showLoading.set(showLoading);
    }
    //endregion

    //region --- Constructors ---
    public FeedViewModel(@NonNull Application application, @NonNull RecyclerViewConfiguration recyclerViewConfiguration,
                         @NonNull LottieViewConfiguration lottieViewConfiguration, @NonNull ConsumerService consumerService,
                         @NonNull IConsumerService iConsumerService) {
        super(application);

        this.recyclerViewConfiguration = recyclerViewConfiguration;
        this.lottieViewConfiguration = lottieViewConfiguration;
        this.consumerService = consumerService;
        this.iConsumerService = iConsumerService;

        configureRecyclerView();
        configureLottieView();
    }
    //endregion

    //region --- Private Methods ---
    private void configureRecyclerView(){
        setAdapter(new CustomRecyclerViewAdapter(new ArrayList<>(), feedImage));
        getRecyclerViewConfiguration().setAdapter(getAdapter());
    }
    private void configureLottieView(){
        getLottieViewConfiguration().setAnimation(R.raw.trail_loader);
        getLottieViewConfiguration().setLoop(true);
        getLottieViewConfiguration().setScale(1.0f);
        getLottieViewConfiguration().setStartDelay(0);
        getLottieViewConfiguration().setSpeed(0.0f);
    }
    private void showSnackbarMessage(@NonNull Integer message) {
        getSnackbarMessage().setValue(message);
    }

    private void setLoadingDialogVisibility(@NonNull Boolean isVisible){
        setShowLoading(isVisible);
    }

    private void performGetFeed(){
        try {
            getIConsumerService().getFeed(checkNotNull(getBreed())).enqueue(new Callback<Feed>() {
                @Override
                public void onResponse(@NonNull Call<Feed> call, @NonNull Response<Feed> response) {
                    try {
                        setLoadingDialogVisibility(false);
                        configureRecyclerView(checkNotNull(response.body()).getPhotos());
                        setHasFeed(true);
                    }catch (NullPointerException e){
                        Log.e(TAG, e.getLocalizedMessage());
                        showSnackbarMessage(R.string.em_api);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Feed> call, @NonNull Throwable t) {
                    setLoadingDialogVisibility(false);
                    Log.e(TAG, t.getLocalizedMessage());
                    showSnackbarMessage(R.string.em_api);
                    setHasFeed(false);
                }
            });
        }catch (NullPointerException e){
            Log.e(TAG, e.getLocalizedMessage());
            showSnackbarMessage(R.string.em_api);
        }
    }

    private void configureRecyclerView(List<String> feedList){
        getAdapter().setFeedList(feedList);
    }
    //endregion

    //region --- Public Methods ---
    public void configureViewModel(String breed){
        setBreed(breed);
    }
    //endregion

    //region --- API Calls ---
    public void getFeed(){
        if(!getHasFeed()) {
            if (getConsumerService().hasInternetConnection(getApplication())) {
                setLoadingDialogVisibility(true);
                performGetFeed();
            } else {
                showSnackbarMessage(R.string.em_noConnection);
            }
        }
    }
    //endregion
}
