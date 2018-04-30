package rrzaniolo.iddog.utils;

import android.databinding.Observable.OnPropertyChangedCallback;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import io.reactivex.Observable;

/*
 * Created by Rodrigo Rodrigues Zaniolo on 4/28/2018.
 * All rights reserved.
 */

public class RxUtils {
    private static RxUtils instance;

    public static RxUtils getInstance(){
        if(instance == null )
            instance = new RxUtils();

        return instance;
    }
    /**
     * Converts an ObservableField to an Observable. Note that setting null value inside
     * ObservableField (except for initial value) throws a NullPointerException.
     * @return Observable that contains the latest value in the ObservableField
     */
    @NonNull
    public <T> Observable<T> toObservable(@NonNull final ObservableField<T> field) {
        return Observable.create(emitter -> {
            T initialValue = field.get();

            if (initialValue != null) {
                emitter.onNext(initialValue);
            }

            final OnPropertyChangedCallback callback = new OnPropertyChangedCallback() {
                @Override
                public void onPropertyChanged(android.databinding.Observable observable, int i) {
                    emitter.onNext(field.get());
                }
            };

            field.addOnPropertyChangedCallback(callback);

            emitter.setCancellable(() -> field.removeOnPropertyChangedCallback(callback));
        });
    }
}
