package rrzaniolo.iddog.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import rrzaniolo.iddog.LiveEvents.OnTabSelected;
import rrzaniolo.iddog.R;
import rrzaniolo.iddog.ViewModelFactory;
import rrzaniolo.iddog.databinding.ActivityHomeBinding;
import rrzaniolo.iddog.databinding.ItemTabLayoutBinding;

import static rrzaniolo.iddog.utils.Preconditions.checkNotNull;

/*
 * Created by Rodrigo Rodrigues Zaniolo on 4/28/2018.
 * All rights reserved.
 */

public class HomeActivity extends AppCompatActivity {

    //region --- Constants ---
    private static String TAG = "HomeActivity";
    //endregion

    //region --- Variables ---
    private ActivityHomeBinding binding;
    private HomeViewModel viewModel;
    //endregion

    //region --- Getters and Setters ---
    private ActivityHomeBinding getBinding() {
        return binding;
    }

    private void setBinding() {
        this.binding = DataBindingUtil.setContentView(HomeActivity.this, R.layout.activity_home);
    }

    private HomeViewModel getViewModel() {
        return viewModel;
    }

    private void setViewModel(HomeViewModel viewModel) {
        this.viewModel = viewModel;
    }
    //endregion

    //region --- Life Cycle ---

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setViewModel(ViewModelFactory.getInstance(getApplication()).create(HomeViewModel.class));
        getViewModel().setUpViewModel(getSupportFragmentManager());

        setUpBiding();

        setUpOnTabSelected();
    }

    //endregion

    //region --- Private Methods ---
    private void setUpBiding(){
        setBinding();
        getBinding().setViewModel(getViewModel());
    }

    private void setUpOnTabSelected(){
        try{
            checkNotNull(checkNotNull(getViewModel()).getOnTabSelected())
                    .observe(HomeActivity.this, (OnTabSelected.onTabSelectedObserver) parameter -> {
                        try{
                            checkNotNull(parameter);
                            setSelectedTabLayout(checkNotNull(parameter.getTab()), checkNotNull(parameter.getState()));
                        }catch (NullPointerException e){
                            Log.e(TAG, e.getLocalizedMessage());
                        }
                    });
        }catch (NullPointerException e){
            Log.e(TAG, e.getLocalizedMessage());
        }
    }

    private void setSelectedTabLayout(TabLayout.Tab tab, boolean selected) {
        ItemTabLayoutBinding binding;

        if (tab.getCustomView() == null)
            binding = DataBindingUtil.inflate(HomeActivity.this.getLayoutInflater(), R.layout.item_tab_layout, null, false);
        else
            binding = DataBindingUtil.findBinding(tab.getCustomView());

        if (binding != null) {
            binding.setEnable(selected);
            binding.setTitle(tab.getText() != null ? tab.getText().toString() : "");

            tab.setCustomView(binding.getRoot());
        }
    }
    //endregion
}
