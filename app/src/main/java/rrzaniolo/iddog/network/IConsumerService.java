package rrzaniolo.iddog.network;

/*
 * Created by rrzaniolo on 30/04/18.
 * Copyright © 2018 rrzaniolo. All rights reserved.
 */


import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rrzaniolo.iddog.network.entries.Dogs;
import rrzaniolo.iddog.network.entries.User;

public interface IConsumerService {

    @POST("signup")
    Call<User> signUp();

    @GET("feed")
    Call<Observable<Response<Dogs>>> getFeed(@Query("category") String breed);
}
