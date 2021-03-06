package rrzaniolo.iddog.login;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Intent;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rrzaniolo.iddog.LiveEvents.LoadingDialog;
import rrzaniolo.iddog.LiveEvents.SnackbarMessage;
import rrzaniolo.iddog.R;
import rrzaniolo.iddog.home.HomeActivity;
import rrzaniolo.iddog.network.ConsumerService;
import rrzaniolo.iddog.network.IConsumerService;
import rrzaniolo.iddog.network.JsonObjectUtils;
import rrzaniolo.iddog.network.entries.SignInResponse;
import rrzaniolo.iddog.network.entries.User;
import rrzaniolo.iddog.utils.Constants;
import rrzaniolo.iddog.utils.RxUtils;
import rrzaniolo.iddog.utils.SharedPreferencesUtils;

import static rrzaniolo.iddog.utils.Preconditions.checkEmail;
import static rrzaniolo.iddog.utils.Preconditions.checkNotNull;
import static rrzaniolo.iddog.utils.Preconditions.isNotNullNorEmpty;

/*
 * Created by Rodrigo Rodrigues Zaniolo on 4/28/2018.
 * All rights reserved.
 */

/**
 * ViewModel for the LoginActivity.
 * */
@SuppressWarnings("unused")
public class LoginViewModel extends AndroidViewModel {

    //region --- Constants ---
    private final String TAG = "LoginViewModel";
    //endregion

    //region --- Variables ---
    private final SnackbarMessage snackbarMessage = new SnackbarMessage();
    private final LoadingDialog loadingDialog = new LoadingDialog();
    private final ConsumerService consumerService;
    private final IConsumerService iConsumerService;
    private final RxUtils rxUtils;
    private final String emailErrorMessage;

    private SharedPreferencesUtils prefUtils;

    private ObservableField<Boolean> isEmailValid = new ObservableField<>(false);
    private ObservableField<Boolean> isEmailError = new ObservableField<>(false);
    private ObservableField<String> email = new ObservableField<>("");
    private ObservableField<String> errorMessage = new ObservableField<>("");
    //endregion

    //region --- Getter and Setters ---
    public SnackbarMessage getSnackbarMessage() {
        return snackbarMessage;
    }

    public LoadingDialog getLoadingDialog() {
        return loadingDialog;
    }

    private ConsumerService getConsumerService() {
        return consumerService;
    }

    private IConsumerService getIConsumerService() {
        return iConsumerService;
    }

    private RxUtils getRxUtils() {
        return rxUtils;
    }

    private String getEmailErrorMessage() {
        return emailErrorMessage;
    }

    private SharedPreferencesUtils getPrefUtils() {
        return prefUtils;
    }

    private void setPrefUtils(SharedPreferencesUtils prefUtils) {
        this.prefUtils = prefUtils;
    }

    public ObservableField<Boolean> getIsEmailValid() {
        return isEmailValid;
    }

    private void setIsEmailValid(Boolean isEmailValid) {
        this.isEmailValid.set(isEmailValid);
    }

    public ObservableField<Boolean> getIsEmailError() {
        return isEmailError;
    }

    private void setIsEmailError(Boolean isEmailError) {
        this.isEmailError.set(isEmailError);
    }

    public ObservableField<String> getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(checkNotNull(email));
    }

    public ObservableField<String> getErrorMessage() {
        return errorMessage;
    }

    private void setErrorMessage(String errorMessage) {
        this.errorMessage.set(errorMessage);
    }

    //endregion

    //region --- Constructors ---
    public LoginViewModel(@NonNull Application application, SharedPreferencesUtils prefUtils,
                          ConsumerService consumerService, IConsumerService iConsumerService,
                          RxUtils rxUtils, String emailErrorMessage) {
        super(application);

        this.consumerService = consumerService;
        this.iConsumerService = iConsumerService;
        this.rxUtils = rxUtils;
        this.emailErrorMessage = emailErrorMessage;

        setPrefUtils(prefUtils);
        setEmailValidation();
        setEmailError();
        setErrorMessageDisplay();
    }
    //endregion

    //region -- Private Methods ---
    @SuppressLint("CheckResult")
    private void setEmailValidation(){
        Observable.combineLatest(
                getRxUtils().toObservable(getEmail()),
                getRxUtils().toObservable(new ObservableField<>(true)),
                (email, control) ->
                        checkEmail(email) && isNotNullNorEmpty(email)
                ).subscribe(this::setIsEmailValid);
    }

    @SuppressLint("CheckResult")
    private void setEmailError(){
        Observable.combineLatest(
                getRxUtils().toObservable(getEmail()),
                getRxUtils().toObservable(new ObservableField<>(true)),
                (email, control) ->
                        !checkEmail(email) && isNotNullNorEmpty(email)
        ).subscribe(this::setIsEmailError);
    }

    @SuppressLint("CheckResult")
    private void setErrorMessageDisplay(){
        Observable.combineLatest(
                getRxUtils().toObservable(getIsEmailError()),
                getRxUtils().toObservable(new ObservableField<>(true)),
                (isError, control) ->
                        isError
        ).subscribe(isError -> {
            if(isError)
                setErrorMessage(getEmailErrorMessage());
            else
                setErrorMessage("");
        });
    }

    private void showSnackbarMessage(@NonNull Integer message) {
        getSnackbarMessage().setValue(message);
    }

    private void setLoadingDialogVisibility(@NonNull Boolean isVisible){
        getLoadingDialog().setValue(isVisible);
    }

    private void goToHome(){
        getApplication().startActivity(new Intent(getApplication(), HomeActivity.class));
    }

    private void saveUserInfo(User user){
        try{
            checkNotNull(getPrefUtils()).putString(
                            Constants.USER_TOKEN,
                            checkNotNull(user).getToken()
                    );
            goToHome();

        }catch(NullPointerException e){
            Log.e(TAG, e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "");
            showSnackbarMessage(R.string.em_api);
        }
    }

    private void performSingUp(){
        try {
            getIConsumerService()
                    .signIn(JsonObjectUtils.signInBody(checkNotNull(getEmail().get())))
                    .enqueue(new Callback<SignInResponse>() {
                @Override
                public void onResponse(@NonNull Call<SignInResponse> call, @NonNull Response<SignInResponse> response) {
                    try {
                        setLoadingDialogVisibility(false);
                        if (response.isSuccessful()) {
                            saveUserInfo(checkNotNull(response.body()).getUser());
                        } else {
                            Log.e(TAG, response.message());
                            showSnackbarMessage(R.string.em_api);
                        }
                    }catch(NullPointerException e){
                        Log.e(TAG, e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "");
                        showSnackbarMessage(R.string.em_api);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<SignInResponse> call, @NonNull Throwable t) {
                    setLoadingDialogVisibility(false);
                    Log.e(TAG, t.getLocalizedMessage());
                    showSnackbarMessage(R.string.em_api);
                }
            });
        }catch(NullPointerException e){
            Log.e(TAG, e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "");
            showSnackbarMessage(R.string.em_api);
        }
    }
    //endregion

    //region --- Public Methods ---
    public View.OnClickListener onLogin(){
        return v -> signUp();
    }
    //endregion

    //region --- API CALL ---
    private void signUp(){
        if(getConsumerService().hasInternetConnection(getApplication())){
            setLoadingDialogVisibility(true);
            performSingUp();
        }else{
            showSnackbarMessage(R.string.em_noConnection);

        }
    }
    //endregion
}
