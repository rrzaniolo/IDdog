package rrzaniolo.iddog.data;

/*
  Created by Rodrigo Rodrigues Zaniolo on 4/28/2018.
  All rights reserved.
 */

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;

/**
 * Data structure used on the OnTabSelected Live event.
 * */
public class TabSelectionParameter {
    private final TabLayout.Tab tab;
    private final Boolean state;

    public TabLayout.Tab getTab() {
        return tab;
    }

    public Boolean getState() {
        return state;
    }

    public TabSelectionParameter(@NonNull TabLayout.Tab tab, @NonNull Boolean state) {
        this.tab = tab;
        this.state = state;
    }
}
