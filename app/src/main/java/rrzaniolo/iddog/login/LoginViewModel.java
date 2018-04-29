package rrzaniolo.iddog.login;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;

import rrzaniolo.iddog.LiveEvents.LoadingDialog;
import rrzaniolo.iddog.R;
import rrzaniolo.iddog.LiveEvents.SnackbarMessage;
import rrzaniolo.iddog.network.RetrofitManager;

import static rrzaniolo.iddog.utils.Preconditions.checkEmail;
import static rrzaniolo.iddog.utils.Preconditions.checkNotNull;

/**
 * Created by Rodrigo Rodrigues Zaniolo on 4/28/2018.
 * All rights reserved.
 */

public class LoginViewModel extends AndroidViewModel {

    //region --- Variables ---
    private final SnackbarMessage snackbarMessage = new SnackbarMessage();
    private final LoadingDialog loadingDialog = new LoadingDialog();

    private ObservableBoolean isEnable = new ObservableBoolean(false);
    private ObservableBoolean isEmailValid = new ObservableBoolean(false);
    private ObservableField<String> email = new ObservableField<>("");
    //endregion

    //region --- Getter and Setters ---
    public SnackbarMessage getSnackbarMessage() {
        return snackbarMessage;
    }

    public LoadingDialog getLoadingDialog() {
        return loadingDialog;
    }

    @SuppressWarnings("unused")
    public ObservableBoolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Boolean isEnable) {
        this.isEnable.set(isEnable);
    }

    public ObservableBoolean getIsEmailValid() {
        return isEmailValid;
    }

    public void setIsEmailValid(Boolean isEmailValid) {
        this.isEmailValid.set(isEmailValid);
    }

    public ObservableField<String> getEmail() {
        return email;
    }

    @SuppressWarnings("unused")
    public void setEmail(String email) {
        this.email.set(checkNotNull(email));
        setIsEmailValid(checkEmail(email));
        setIsEnable(getIsEmailValid().get());
    }
    //endregion

    //region --- Constructors ---
    public LoginViewModel(@NonNull Application application) {
        super(application);
    }
    //endregion

    //region -- Private Methods ---
    private void showSnackbarMessage(@NonNull Integer message) {
        getSnackbarMessage().setValue(message);
    }
    private void setLoadingDialogVisibility(@NonNull Boolean isVisible){
        getLoadingDialog().setValue(isVisible);
    }
    //endregion

    //region --- Public Methods ---
    public View.OnClickListener onLogin(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(new RetrofitManager());
            }
        };
    }
    //endregion

    //region --- API CALL ---
    private void signIn (RetrofitManager retrofitManager){
        if(retrofitManager.hasInternetConnection(getApplication())){
            showSnackbarMessage(R.string.em_noConnection);
        }else{
            setLoadingDialogVisibility(true);
            showSnackbarMessage(R.string.em_apiSignIn);
        }
    }
}
