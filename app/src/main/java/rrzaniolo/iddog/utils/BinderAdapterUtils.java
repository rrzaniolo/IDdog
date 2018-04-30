package rrzaniolo.iddog.utils;

/*
 * Created by rrzaniolo on 30/04/18.
 * Copyright © 2018 rrzaniolo. All rights reserved.
 */

import android.databinding.BindingAdapter;
import android.support.design.widget.TextInputLayout;

public class BinderAdapterUtils {

    @BindingAdapter("errorEnabled")
    public static void setErrorEnabled(TextInputLayout textInputLayout, Boolean errorEnabled) {
        textInputLayout.setErrorEnabled(errorEnabled);
    }

    @BindingAdapter("error")
    public static void setError(TextInputLayout textInputLayout, String error) {
        textInputLayout.setError(error);
    }
}
