package rrzaniolo.iddog.home.feed;

/*
 * Created by rrzaniolo on 02/05/18.
 * Copyright © 2018 rrzaniolo. All rights reserved.
 */

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rrzaniolo.iddog.LiveEvents.LoadingDialog;
import rrzaniolo.iddog.LiveEvents.SnackbarMessage;
import rrzaniolo.iddog.R;
import rrzaniolo.iddog.network.ConsumerService;
import rrzaniolo.iddog.network.IConsumerService;
import rrzaniolo.iddog.network.entries.Feed;

import static rrzaniolo.iddog.utils.Preconditions.checkNotNull;

public class FeedViewModel extends AndroidViewModel{

    //region --- Constants ---
    private static String TAG = "FeedViewModel";

    //region --- Variables ---
    private final SnackbarMessage snackbarMessage = new SnackbarMessage();
    private final LoadingDialog loadingDialog = new LoadingDialog();
    private final ConsumerService consumerService;
    private final IConsumerService iConsumerService;

    private String breed = "";
    private Boolean hasFeed = false;
    //endregion

    //region --- Getters and Setters ---
    public SnackbarMessage getSnackbarMessage() {
        return snackbarMessage;
    }

    public LoadingDialog getLoadingDialog() {
        return loadingDialog;
    }

    public ConsumerService getConsumerService() {
        return consumerService;
    }

    public IConsumerService getIConsumerService() {
        return iConsumerService;
    }

    public String getBreed() {
        return breed;
    }

    private void setBreed(String breed) {
        this.breed = breed;
    }

    public Boolean getHasFeed() {
        return hasFeed;
    }

    public void setHasFeed(Boolean hasFeed) {
        this.hasFeed = hasFeed;
    }
    //endregion

    //region --- Constructors ---
    public FeedViewModel(@NonNull Application application,
                         @NonNull ConsumerService consumerService, @NonNull IConsumerService iConsumerService) {
        super(application);

        this.consumerService = consumerService;
        this.iConsumerService = iConsumerService;
    }
    //endregion

    //region --- Private Methods ---
    private void showSnackbarMessage(@NonNull Integer message) {
        getSnackbarMessage().setValue(message);
    }

    private void setLoadingDialogVisibility(@NonNull Boolean isVisible){
        getLoadingDialog().setValue(isVisible);
    }

    private void performGetFeed(){
        try {
            getIConsumerService().getFeed(checkNotNull(getBreed())).enqueue(new Callback<Feed>() {
                @Override
                public void onResponse(@NonNull Call<Feed> call, @NonNull Response<Feed> response) {
                    setLoadingDialogVisibility(false);
                    showSnackbarMessage(R.string.app_name);
                    setHasFeed(true);
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
