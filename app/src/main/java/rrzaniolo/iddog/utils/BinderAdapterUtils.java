package rrzaniolo.iddog.utils;

/*
 * Created by rrzaniolo on 30/04/18.
 * Copyright © 2018 rrzaniolo. All rights reserved.
 */

import android.databinding.BindingAdapter;
import android.graphics.Typeface;
import android.support.annotation.AnimRes;
import android.support.annotation.FontRes;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.TextView;

import rrzaniolo.iddog.base.GridViewConfiguration;
import rrzaniolo.iddog.base.TabLayoutConfiguration;
import rrzaniolo.iddog.base.ViewPagerConfiguration;

public class BinderAdapterUtils {

    @BindingAdapter("errorEnabled")
    public static void setErrorEnabled(TextInputLayout textInputLayout, Boolean errorEnabled) {
        textInputLayout.setErrorEnabled(errorEnabled);
    }

    @BindingAdapter("error")
    public static void setError(TextInputLayout textInputLayout, String error) {
        textInputLayout.setError(error);
    }

    @BindingAdapter("viewPager_pageMargin")
    public static void setPageMargin(ViewPager viewPager, int margin) {
        viewPager.setPageMargin(margin);
    }

    @BindingAdapter("viewPager_offscreenPageLimit")
    public static void setOffscreenPageLimit(ViewPager viewPager, int limit) {
        viewPager.setOffscreenPageLimit(limit);
    }

    @BindingAdapter(value = {"viewPager_currentItem","viewPager_smoothScroll"})
    public static void setCurrentItem(ViewPager viewPager, int item, boolean smoothScroll) {
        viewPager.setCurrentItem(item, smoothScroll);
    }

    @BindingAdapter("viewPager_configuration")
    public static void configureViewPager(ViewPager viewPager,
                                          ViewPagerConfiguration viewPagerConfiguration) {
        viewPager.setAdapter(viewPagerConfiguration.getAdapter());
        viewPager.addOnPageChangeListener(viewPagerConfiguration.getOnPageChangeListener());
        viewPager.setPageTransformer(false, viewPagerConfiguration.getPageTransformer());
    }

    @BindingAdapter("tabLayout_configuration")
    public static void configureViewPager(TabLayout tabLayout, TabLayoutConfiguration tabLayoutConfiguration) {
        tabLayout.addOnTabSelectedListener(tabLayoutConfiguration.getOnTabSelectedListener());
    }

    @BindingAdapter("gridView_configuration")
    public static void configureGridView(GridView gridView, GridViewConfiguration gridViewConfiguration){
        gridView.setAdapter(gridViewConfiguration.getAdapter());
        gridView.setOnItemClickListener(gridViewConfiguration.getClickListener());
    }

    @BindingAdapter(value = {"animation", "visibility", "visibilityType"})
    public static void setAnimation(View view, @AnimRes int animationRes, final boolean visibility, final int visibilityType) {
        Animation animation = AnimationUtils.loadAnimation(view.getContext(), animationRes);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (visibility)
                    view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (!visibility)
                    view.setVisibility(visibilityType);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animation);
    }

    @BindingAdapter("fontFamily")
    public static void setFontFamily(TextView textView, @FontRes int fontFamily) {
        Typeface typeface = ResourcesCompat.getFont(textView.getContext(), fontFamily);
        textView.setTypeface(typeface);
    }
}
