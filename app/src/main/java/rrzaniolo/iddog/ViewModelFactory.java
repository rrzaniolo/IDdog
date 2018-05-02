package rrzaniolo.iddog;

/*
  Created by Rodrigo Rodrigues Zaniolo on 4/28/2018.
  All rights reserved.
 */

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import rrzaniolo.iddog.base.TabLayoutConfiguration;
import rrzaniolo.iddog.base.ViewPagerConfiguration;
import rrzaniolo.iddog.home.HomeViewModel;
import rrzaniolo.iddog.home.feed.FeedViewModel;
import rrzaniolo.iddog.login.LoginViewModel;
import rrzaniolo.iddog.network.ConsumerService;
import rrzaniolo.iddog.utils.RxUtils;
import rrzaniolo.iddog.utils.SharedPreferencesUtils;

/**
 * A creator is used to inject the product ID into the ViewModel
 * <p>
 * This creator is to showcase how to inject dependencies into ViewModels. It's not
 * actually necessary in this case, as the product ID can be passed in a public method.
 */
public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    @SuppressLint("StaticFieldLeak")
    private static volatile ViewModelFactory INSTANCE;

    private final Application application;

    private Application getApplication() {
        return application;
    }

    public static ViewModelFactory getInstance(Application application) {

        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(application);
                }
            }
        }
        return INSTANCE;
    }

    private ViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            //noinspection unchecked
            return (T) new LoginViewModel(
                    getApplication(),
                    new SharedPreferencesUtils(getApplication()),
                    new ConsumerService(),
                    ConsumerService.getInstance(getApplication()),
                    RxUtils.getInstance(),
                    getApplication().getString(R.string.em_email));
        } else if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            //noinspection unchecked
            return (T) new HomeViewModel(
                    getApplication(),
                    new ViewPagerConfiguration(),
                    new TabLayoutConfiguration());
        } else if(modelClass.isAssignableFrom(FeedViewModel.class)){
            //noinspection unchecked
            return (T) new FeedViewModel(
                    getApplication(),
                    new ConsumerService(),
                    ConsumerService.getInstance(getApplication())
            );
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
