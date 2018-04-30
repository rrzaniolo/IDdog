package rrzaniolo.iddog.utils;

/*
  Created by Rodrigo Rodrigues Zaniolo on 4/28/2018.
  All rights reserved.
 */

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;

import rrzaniolo.iddog.base.LoadingDialogFragment;


/**
 * Provides a method to show or hide a LoadingDialog.
 */
public class LoadingDialogUtils {
    public static void changeLoadingDialogVisibility(View v, Boolean isVisible, String message, FragmentManager fragmentManager) {
        if (v == null || isVisible == null) {
            return;
        }

        if(isVisible)
            showLoadingDialog(fragmentManager, message);
        else
            dismissLoadingDialog(fragmentManager);
    }

    private static void showLoadingDialog(FragmentManager fragmentManager, String message) {
        LoadingDialogFragment loadingDialogFragment = (LoadingDialogFragment) fragmentManager.findFragmentByTag(LoadingDialogFragment.class.getName());

        if (loadingDialogFragment == null) {
            Bundle bundle = new Bundle(1);
            bundle.putString(Constants.BUNDLE_LOADING_MESSAGE, message);

            loadingDialogFragment = LoadingDialogFragment.newInstance();
            loadingDialogFragment.setArguments(bundle);
            loadingDialogFragment.setCancelable(false);
            loadingDialogFragment.show(fragmentManager, loadingDialogFragment.getClass().getName());
        }
    }

    private static void dismissLoadingDialog(FragmentManager fragmentManager) {
        LoadingDialogFragment loadingDialogFragment = (LoadingDialogFragment) fragmentManager.findFragmentByTag(LoadingDialogFragment.class.getName());

        if (loadingDialogFragment != null) {
            loadingDialogFragment.dismiss();
        }
    }
}
