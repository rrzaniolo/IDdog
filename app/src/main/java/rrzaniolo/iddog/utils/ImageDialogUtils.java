package rrzaniolo.iddog.utils;

/*
  Created by Rodrigo Rodrigues Zaniolo on 4/28/2018.
  All rights reserved.
 */

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;

import rrzaniolo.iddog.base.ExpandedImageFragment;


/**
 * Provides a method to show or hide a ExpandedImageFragment.
 */
public class ImageDialogUtils {
    public static void changeImageDialogVisibility(View v, Boolean isVisible, String url, FragmentManager fragmentManager) {
        if (v == null || isVisible == null) {
            return;
        }

        if(isVisible)
            showImageDialog(fragmentManager, url);
        else
            dismissImageDialog(fragmentManager);
    }

    private static void showImageDialog(FragmentManager fragmentManager, String url) {
        ExpandedImageFragment expandedImageFragment = (ExpandedImageFragment) fragmentManager.findFragmentByTag(ExpandedImageFragment.class.getName());

        if (expandedImageFragment == null) {
            Bundle bundle = new Bundle(1);
            bundle.putString(Constants.BUNDLE_IMAGE_URL, url);

            expandedImageFragment = ExpandedImageFragment.newInstance();
            expandedImageFragment.setArguments(bundle);
            expandedImageFragment.setCancelable(false);
            expandedImageFragment.show(fragmentManager, expandedImageFragment.getClass().getName());
        }
    }

    private static void dismissImageDialog(FragmentManager fragmentManager) {
        ExpandedImageFragment expandedImageFragment = (ExpandedImageFragment) fragmentManager.findFragmentByTag(ExpandedImageFragment.class.getName());

        if (expandedImageFragment != null) {
            expandedImageFragment.dismiss();
        }
    }
}
