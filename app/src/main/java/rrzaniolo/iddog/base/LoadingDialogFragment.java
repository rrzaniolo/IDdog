package rrzaniolo.iddog.base;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import rrzaniolo.iddog.BR;
import rrzaniolo.iddog.R;
import rrzaniolo.iddog.databinding.DialogFragmentLoadingBinding;
import rrzaniolo.iddog.utils.Constants;

import static rrzaniolo.iddog.utils.Preconditions.checkNotNull;

/**
 * A DialogFragment with loading animation.
 * */
public class LoadingDialogFragment extends DialogFragment {
    //region --- Constants ---
    @SuppressWarnings("FieldCanBeLocal")
    private final String TAG = "LoadingDialog";
    //endregion

    //region --- Constructors ---
    public LoadingDialogFragment() {}

    public static LoadingDialogFragment newInstance() {
        return new LoadingDialogFragment();
    }
    //endregion

    //region --- Life Cycle ---
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String message;
        try {
            checkNotNull(getDialog().getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
             message = checkNotNull(getArguments()).getString(Constants.BUNDLE_LOADING_MESSAGE, "");
        }catch (NullPointerException e){
            Log.e(TAG, e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "");
            message = "";
        }


        DialogFragmentLoadingBinding dialogFragmentLoadingBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_fragment_loading, container, false);
        dialogFragmentLoadingBinding.setVariable(BR.message, message);

        dialogFragmentLoadingBinding.dLoadingPbLoading.loop(true);
        dialogFragmentLoadingBinding.dLoadingPbLoading.playAnimation();

        return dialogFragmentLoadingBinding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            checkNotNull(getView()).getRootView().setBackgroundColor(ContextCompat.getColor(getView().getContext(), R.color.transparent));
            checkNotNull(checkNotNull(getDialog()).getWindow()).setBackgroundDrawableResource(R.color.transparent);
        }catch (NullPointerException e){
            Log.e(TAG, e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "");
        }
    }
    //endregion

    @Override
    public void show(FragmentManager manager, String tag) {
        if (manager.findFragmentByTag(tag) == null)
            try {
                super.show(manager, tag);
            } catch (IllegalStateException e) {
                manager.beginTransaction().add(this, tag).commitAllowingStateLoss();
            }
    }

    @Override
    public void dismiss() {
        super.dismissAllowingStateLoss();
    }
}
