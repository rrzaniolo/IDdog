package rrzaniolo.iddog.base;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import rrzaniolo.iddog.R;
import rrzaniolo.iddog.base.configurations.LottieViewConfiguration;
import rrzaniolo.iddog.databinding.DialogFragmentImageBinding;
import rrzaniolo.iddog.utils.Constants;

import static rrzaniolo.iddog.utils.Preconditions.checkNotNull;

/**
 * A DialogFragment with loading animation.
 * */
public class ExpandedImageFragment extends DialogFragment {
    //region --- Constants ---
    @SuppressWarnings("FieldCanBeLocal")
    private final String TAG = "ExpandedImage";
    private DialogFragmentImageBinding binding;
    private String url;

    private ObservableBoolean showLoading = new ObservableBoolean(false);
    private LottieViewConfiguration configuration = new LottieViewConfiguration();
    //endregion

    //region --- Getters and Setters ---
    public ObservableBoolean getShowLoading() {
        return showLoading;
    }

    public void setShowLoading(Boolean showLoading) {
        this.showLoading.set(showLoading);
    }

    public LottieViewConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(LottieViewConfiguration configuration) {
        this.configuration = configuration;
    }
    //endregion

    //region --- Constructors ---
    public ExpandedImageFragment() {
        getConfiguration().setLoop(true);
        getConfiguration().setAnimation(R.raw.loader);
    }

    public static ExpandedImageFragment newInstance() {
        return new ExpandedImageFragment();
    }
    //endregion

    //region --- Life Cycle ---
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            checkNotNull(getDialog().getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
             url = checkNotNull(getArguments()).getString(Constants.BUNDLE_IMAGE_URL, "");
        }catch (NullPointerException e){
            Log.e(TAG, e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "");
            url = "";
        }

        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_fragment_image, container,false);

        binding.setShowLoading(showLoading);
        binding.setConfiguration(getConfiguration());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            checkNotNull(getView()).getRootView().setBackgroundColor(ContextCompat.getColor(getView().getContext(), R.color.transparent));

            setShowLoading(true);
            Glide.with(getView().getContext())
                    .load(checkNotNull(url))
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            setShowLoading(false);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            setShowLoading(false);
                            return false;
                        }
                    })
                    .into(checkNotNull(binding).dImageIv);

            checkNotNull(checkNotNull(getDialog()).getWindow()).setBackgroundDrawableResource(R.color.transparent);
            getDialog().setCancelable(true);

            binding.dImageIv.setOnClickListener(v -> getDialog().dismiss());

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
