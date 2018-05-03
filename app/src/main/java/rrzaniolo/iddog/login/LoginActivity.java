package rrzaniolo.iddog.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import rrzaniolo.iddog.LiveEvents.LoadingDialog;
import rrzaniolo.iddog.R;
import rrzaniolo.iddog.LiveEvents.SnackbarMessage;
import rrzaniolo.iddog.ViewModelFactory;
import rrzaniolo.iddog.databinding.ActivityLoginBinding;
import rrzaniolo.iddog.utils.LoadingDialogUtils;
import rrzaniolo.iddog.utils.SnackbarUtils;

import static rrzaniolo.iddog.utils.Preconditions.checkNotNull;

/*
 * Created by Rodrigo Rodrigues Zaniolo on 4/28/2018.
 * All rights reserved.
 */

/**
 * Activity that handles user Login.
 * */
public class LoginActivity extends AppCompatActivity {

    //region --- Constants ---
    private final String TAG = "LoginActivity";
    //endregion

    //region --- Variables ---
    private LoginViewModel viewModel;
    private ActivityLoginBinding binding;
    //endregion

    //region --- Getters and Setters ---
    private LoginViewModel getViewModel() {
        return viewModel;
    }

    private void setViewModel(LoginViewModel viewModel) {
        this.viewModel = viewModel;
    }

    private ActivityLoginBinding getBinding() {
        return binding;
    }

    private void setBinding() {
        this.binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
    }
    //endregion

    //region --- Life Cycle ---
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setViewModel(ViewModelFactory.getInstance(getApplication()).create(LoginViewModel.class));
        setUpBinding();
        setUpSnackBar();
        setUpLoadingDialog();
    }
    //endregion

    //region --- Private Methods ---
    private void setUpBinding(){
        setBinding();
        getBinding().setViewModel(getViewModel());
    }
    private void setUpSnackBar(){
        try {
            checkNotNull(checkNotNull(getViewModel().getSnackbarMessage()))
                    .observe(LoginActivity.this, (SnackbarMessage.SnackbarObserver) messageResourceId -> {
                        try {
                            SnackbarUtils.showSnackbar(checkNotNull(checkNotNull(getBinding()).getRoot()), getString(messageResourceId));
                        } catch (NullPointerException e) {
                            Log.e(TAG, e.getLocalizedMessage());
                        }
                    });
        }catch(NullPointerException e){
            Log.e(TAG, e.getLocalizedMessage());
        }
    }

    private void setUpLoadingDialog(){
        try {
            checkNotNull(checkNotNull(getViewModel()).getLoadingDialog())
                    .observe(LoginActivity.this, new LoadingDialog.LoadingDialogObserver() {
                @Override
                public void onVisibilityChange(@NonNull Boolean isVisible) {
                    try {
                        LoadingDialogUtils.changeLoadingDialogVisibility(
                                checkNotNull(checkNotNull(getBinding()).getRoot()),
                                isVisible,
                                "",
                                getSupportFragmentManager());
                    } catch (NullPointerException e) {
                        Log.e(TAG, e.getLocalizedMessage());
                    }
                }
            });
        }catch(NullPointerException e){
            Log.e(TAG, e.getLocalizedMessage());
        }
    }
    //endregion
}
