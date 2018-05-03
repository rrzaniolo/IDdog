package rrzaniolo.iddog.base;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;

import rrzaniolo.iddog.R;

/*
 * Created by rrzaniolo on 30/04/18.
 * Copyright © 2018 rrzaniolo. All rights reserved.
 */

/**
 * A TextInputLayout with style already applied.
 * */
public class CustomTextInputLayout extends TextInputLayout {

    public CustomTextInputLayout(Context context) {
        super(context);
        init();
    }

    public CustomTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setHintTextAppearance(R.style.TextInputLayoutHint);
    }
}
