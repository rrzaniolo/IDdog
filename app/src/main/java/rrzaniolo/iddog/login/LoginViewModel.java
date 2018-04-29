package rrzaniolo.iddog.login;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.view.View;

import rrzaniolo.iddog.R;
import rrzaniolo.iddog.LiveEvents.SnackbarMessage;
import rrzaniolo.iddog.network.RetrofitManager;

/**
 * Created by Rodrigo Rodrigues Zaniolo on 4/28/2018.
 * All rights reserved.
 */

public class LoginViewModel extends AndroidViewModel {

    //region --- Variables ---
    private final SnackbarMessage snackbarMessage = new SnackbarMessage();
    //endregion

    //region --- Getter and Setters ---
    public SnackbarMessage getSnackbarMessage() {
        return snackbarMessage;
    }
    //endregion

    //region --- Constructors ---
    public LoginViewModel(@NonNull Application application) {
        super(application);
    }
    //endregion

    //region -- Private Methods ---
    private void showSnackbarMessage(Integer message) {
        getSnackbarMessage().setValue(message);
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
            showSnackbarMessage(R.string.em_apiSignIn);
        }
    }
}
