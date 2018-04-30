package rrzaniolo.iddog.login;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rrzaniolo.iddog.LiveEvents.LoadingDialog;
import rrzaniolo.iddog.LiveEvents.SnackbarMessage;
import rrzaniolo.iddog.R;
import rrzaniolo.iddog.network.ConsumerService;
import rrzaniolo.iddog.network.IConsumerService;
import rrzaniolo.iddog.network.entries.User;
import rrzaniolo.iddog.utils.Constants;
import rrzaniolo.iddog.utils.SharedPreferencesUtils;

import static rrzaniolo.iddog.utils.Preconditions.checkEmail;
import static rrzaniolo.iddog.utils.Preconditions.checkNotNull;

/**
 * Created by Rodrigo Rodrigues Zaniolo on 4/28/2018.
 * All rights reserved.
 */

public class LoginViewModel extends AndroidViewModel {

    //region --- Constants ---
    private final String TAG = "LoginViewModel";
    //endregion

    //region --- Variables ---
    private final SnackbarMessage snackbarMessage = new SnackbarMessage();
    private final LoadingDialog loadingDialog = new LoadingDialog();

    private SharedPreferencesUtils prefUtils;

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

    private SharedPreferencesUtils getPrefUtils() {
        return prefUtils;
    }

    private void setPrefUtils(SharedPreferencesUtils prefUtils) {
        this.prefUtils = prefUtils;
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
    public LoginViewModel(@NonNull Application application, SharedPreferencesUtils prefUtils) {
        super(application);
        setPrefUtils(prefUtils);
    }
    //endregion

    //region -- Private Methods ---
    private void showSnackbarMessage(@NonNull Integer message) {
        getSnackbarMessage().setValue(message);
    }

    private void setLoadingDialogVisibility(@NonNull Boolean isVisible){
        getLoadingDialog().setValue(isVisible);
    }

    private void goToHome(){
        //TODO:
    }

    private void saveUserInfo(User user){
        try{
            checkNotNull(getPrefUtils()).putString(
                            Constants.USER_TOKEN,
                            checkNotNull(user).getToken()
                    );
            goToHome();

        }catch(NullPointerException e){
            Log.e(TAG, e.getLocalizedMessage());
            showSnackbarMessage(R.string.em_apiSignUp);
        }
    }

    private void performSingUp(IConsumerService instance){
        instance.signUp().enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                setLoadingDialogVisibility(false);
                if(response.isSuccessful()){
                    saveUserInfo(response.body());
                }else{
                    Log.e(TAG, response.message());
                    showSnackbarMessage(R.string.em_apiSignUp);
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                setLoadingDialogVisibility(false);
                Log.e(TAG, t.getLocalizedMessage());
                showSnackbarMessage(R.string.em_apiSignUp);
            }
        });
    }
    //endregion

    //region --- Public Methods ---
    public View.OnClickListener onLogin(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUn(new ConsumerService());
            }
        };
    }
    //endregion

    //region --- API CALL ---
    private void signUn(ConsumerService consumerService){
        if(consumerService.hasInternetConnection(getApplication())){
            showSnackbarMessage(R.string.em_noConnection);
        }else{
            setLoadingDialogVisibility(true);
            performSingUp(ConsumerService.getInstance(getApplication()));
        }
    }
    //endregion
}
