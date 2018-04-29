package rrzaniolo.iddog.network;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

//import io.reactivex.Observa
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rrzaniolo.iddog.BuildConfig;
import rrzaniolo.iddog.utils.Constants;

import static rrzaniolo.iddog.utils.Preconditions.checkNotNull;

/**
 * Created by Rodrigo Rodrigues Zaniolo on 4/29/2018.
 * All rights reserved.
 */
public class RetrofitManager {

    //region --- Constants ---
    private static String TAG = "RetrofitManager";
    //endregion

    //region --- Variables ---
    private static volatile Retrofit retrofit = null;
    private static volatile OkHttpClient okHttpClient = null;
    //endregion

    //region --- Constructors ---
    public RetrofitManager() { }
    //endregion

    /* Private Methods. */
    private static OkHttpClient getOkHttpClient(){
        if(okHttpClient == null) {
            synchronized (RequestManager.class) {
                if (okHttpClient == null) {
                    if (BuildConfig.DEBUG) {
                        okHttpClient = new OkHttpClient.Builder()
                                .addNetworkInterceptor(new LoggingInterceptor())
                                .connectTimeout(30, TimeUnit.SECONDS)
                                .writeTimeout(30, TimeUnit.SECONDS)
                                .readTimeout(30, TimeUnit.SECONDS)
                                .build();
                    } else {
                        okHttpClient = new OkHttpClient.Builder()
                                .connectTimeout(30, TimeUnit.SECONDS)
                                .writeTimeout(30, TimeUnit.SECONDS)
                                .readTimeout(30, TimeUnit.SECONDS)
                                .build();
                    }
                }
            }
        }
        return okHttpClient;
    }

    private Retrofit getRetrofit(){
        if(retrofit == null) {
            synchronized (RequestManager.class){
                if(retrofit == null){
                    Gson gson = new GsonBuilder()
                            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                            .create();

                    retrofit = new Retrofit.Builder()
                            .baseUrl(Constants.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .client(getOkHttpClient())
                            .build();
                }
            }
        }

        return retrofit;
    }

    /* Public Method. */

    /**
     * Check for internet availability.
     * @param context - Context.
     * @return if the user has or no internet.
     */
    public boolean hasInternetConnection(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = checkNotNull(connectivityManager).getActiveNetworkInfo();

            return checkNotNull(networkInfo).isConnectedOrConnecting();
        }catch (NullPointerException e){
            Log.e(TAG, e.getLocalizedMessage());
            return false;
        }
    }

    /* Inner Class. */
    private static class LoggingInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(@NonNull Interceptor.Chain chain) throws IOException {

            Request request = chain.request();
            long t1 = System.nanoTime();
            Log.d(TAG, String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));

            okhttp3.Response response = chain.proceed(request);
            long t2 = System.nanoTime();
            Log.d(TAG, String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));

            return response;
        }
    }
}
