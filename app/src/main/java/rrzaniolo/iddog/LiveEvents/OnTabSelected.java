package rrzaniolo.iddog.LiveEvents;

/*
  Created by Rodrigo Rodrigues Zaniolo on 4/28/2018.
  All rights reserved.
 */

import android.arch.lifecycle.LifecycleOwner;

import rrzaniolo.iddog.data.TabSelectionParameter;

/**
 * A rrzaniolo.iddog.LiveEvents.SingleLiveEvent used for TabSelected events. Like a {@link SingleLiveEvent} but also
 * uses a custom observer.
 * <p>
 * Note that only one observer is going to be notified of changes.
 */
public class OnTabSelected extends SingleLiveEvent<TabSelectionParameter> {
    public void observe(LifecycleOwner owner, final onTabSelectedObserver observer) {
        super.observe(owner, t -> {
            if (t == null) {
                return;
            }
            observer.onTabSelected(t);
        });
    }

    public interface onTabSelectedObserver {
        /**
         * Called when there is a new tab is to be shown.
         * @param parameter The new tab and it's state, non-null.
         */
        void onTabSelected(TabSelectionParameter parameter);
    }
}

