package rrzaniolo.iddog.network;

/*
 * Created by Rodrigo Rodrigues Zaniolo on 4/29/2018.
 * All rights reserved.
 */

import android.support.annotation.NonNull;

import com.google.gson.JsonObject;

/**
 * Calls to handle JsonObject creation for API calls.
 */
public final class JsonObjectUtils {

    @NonNull public static
    JsonObject signInBody(@NonNull String email){
        JsonObject body = new JsonObject();
        body.addProperty("email", email);
        return body;
    };
}
