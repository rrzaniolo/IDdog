package rrzaniolo.iddog.utils;

import android.support.annotation.NonNull;
import android.util.Patterns;

/**
 * Created by Rodrigo Rodrigues Zaniolo on 4/28/2018.
 * All rights reserved.
 */

public final class Preconditions {
    private Preconditions() {}

    /**
     * Ensures that an object reference passed as a parameter to the calling
     * method is not null.
     *
     * @param reference an object reference
     * @return the non-null reference that was validated
     * @throws NullPointerException if {@code reference} is null
     */
    public static @NonNull
    <T> T checkNotNull(final T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    public static @NonNull
    Boolean checkEmail(@NonNull final String email){
      return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    };
}
