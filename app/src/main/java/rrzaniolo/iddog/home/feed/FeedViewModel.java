package rrzaniolo.iddog.home.feed;

/*
 * Created by rrzaniolo on 02/05/18.
 * Copyright © 2018 rrzaniolo. All rights reserved.
 */

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

public class FeedViewModel extends AndroidViewModel{

    //region --- Variables ---
    private String breed = "";
    //endregion

    //region --- Getters and Setters ---
    public String getBreed() {
        return breed;
    }

    private void setBreed(String breed) {
        this.breed = breed;
    }
    //endregion

    //region --- Constructors ---
    public FeedViewModel(@NonNull Application application) {
        super(application);
    }
    //endregion

    //region --- Private Methods ---
    //endregion

    //region --- Public Methods ---
    public void configureViewModel(String breed){
        setBreed(breed);
    }
    //endregion

    //region --- API Calls ---
    //endregion
}
