package rrzaniolo.iddog.network;

/*
 * Created by rrzaniolo on 30/04/18.
 * Copyright © 2018 rrzaniolo. All rights reserved.
 */


import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rrzaniolo.iddog.network.entries.Feed;
import rrzaniolo.iddog.network.entries.SignInResponse;

/**
 * Interface that hols all API calls signatures.
 * */
public interface IConsumerService {

    @POST("signup")
    Call<SignInResponse> signIn(@Body JsonObject body);

    @GET("feed")
    Call<Feed> getFeed(@Query("category") String breed);
}
