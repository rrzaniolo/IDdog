package rrzaniolo.iddog.network.entries;

import com.google.gson.annotations.SerializedName;

/*
 * Created by rrzaniolo on 30/04/18.
 * Copyright © 2018 rrzaniolo. All rights reserved.
 */

/**
 * Model for the Response of the SignIn.
 * */
@SuppressWarnings("unused")
public class SignInResponse {

    @SerializedName("user")
    private User user;

    public SignInResponse() { }

    public SignInResponse(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
