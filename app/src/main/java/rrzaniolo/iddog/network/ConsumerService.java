package rrzaniolo.iddog.network;

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

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rrzaniolo.iddog.BuildConfig;
import rrzaniolo.iddog.utils.Constants;
import rrzaniolo.iddog.utils.SharedPreferencesUtils;

import static rrzaniolo.iddog.utils.Preconditions.checkNotNull;
import static rrzaniolo.iddog.utils.Preconditions.isNotNullNorEmpty;

//import io.reactivex.Observa

/*
 * Created by Rodrigo Rodrigues Zaniolo on 4/29/2018.
 * All rights reserved.
 */

/**
 * Class responsible for handling Internet connection and API calls.
 * */
public class ConsumerService {

    //region --- Constants ---
    private static String TAG = "ConsumerService";
    //endregion

    //region --- Variables ---
    private static volatile Retrofit retrofit = null;
    private static volatile OkHttpClient okHttpClient = null;
    private static IConsumerService iConsumerServiceAPI;
    //endregion

    //region --- Constructors ---
    public ConsumerService() { }
    //endregion

    //region --- Private Methods ---
    private static OkHttpClient getOkHttpClient(Context context){
        if(okHttpClient == null) {
            synchronized (RequestManager.class) {
                if (okHttpClient == null) {
                    if (BuildConfig.DEBUG) {
                        okHttpClient = new OkHttpClient.Builder()
                                .addNetworkInterceptor(new LoggingInterceptor())
                                .addInterceptor(new HeaderInterceptor(context))
                                .connectTimeout(30, TimeUnit.SECONDS)
                                .writeTimeout(30, TimeUnit.SECONDS)
                                .readTimeout(30, TimeUnit.SECONDS)
                                .build();
                    } else {
                        okHttpClient = new OkHttpClient.Builder()
                                .connectTimeout(30, TimeUnit.SECONDS)
                                .addInterceptor(new HeaderInterceptor(context))
                                .writeTimeout(30, TimeUnit.SECONDS)
                                .readTimeout(30, TimeUnit.SECONDS)
                                .build();
                    }

                }
            }
        }
        return okHttpClient;
    }

    private static Retrofit getRetrofit(Context context){
        if(retrofit == null) {
            synchronized (RequestManager.class){
                if(retrofit == null){
                    Gson gson = new GsonBuilder()
                            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                            .create();

                    retrofit = new Retrofit.Builder()
                            .baseUrl(Constants.BASE_URL)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .client(getOkHttpClient(context))
                            .build();
                }
            }
        }

        return retrofit;
    }
    //endregion

    //region --- Public Methods ---
    public static synchronized IConsumerService getInstance(Context context) {
        if (iConsumerServiceAPI == null)
            iConsumerServiceAPI = getRetrofit(context).create(IConsumerService.class);

        return iConsumerServiceAPI;
    }

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
            Log.e(TAG, e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "");
            return false;
        }
    }
    //endregion

    //region --- Interceptors ---
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

    private static class HeaderInterceptor implements Interceptor {

        private Context context;
        HeaderInterceptor(Context context) { this.context = context;}

        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request original = chain.request();

            String idToken = new SharedPreferencesUtils(context).getString(Constants.USER_TOKEN);

            Request request = original.newBuilder()
                    .header("Content-Type", "application/json")
                    .header("Authorization", isNotNullNorEmpty(idToken) ? idToken : "")
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);
        }
    }
    //endregion
}
