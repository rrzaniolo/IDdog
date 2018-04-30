package rrzaniolo.iddog.LiveEvents;

/*
  Created by Rodrigo Rodrigues Zaniolo on 4/28/2018.
  All rights reserved.
 */

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.StringRes;

/**
 * A rrzaniolo.iddog.LiveEvents.SingleLiveEvent used for Snackbar messages. Like a {@link SingleLiveEvent} but also prevents
 * null messages and uses a custom observer.
 * <p>
 * Note that only one observer is going to be notified of changes.
 */
public class SnackbarMessage extends SingleLiveEvent<Integer> {
    public void observe(LifecycleOwner owner, final SnackbarObserver observer) {
        super.observe(owner, t -> {
            if (t == null) {
                return;
            }
            observer.onNewMessage(t);
        });
    }

    public interface SnackbarObserver {
        /**
         * Called when there is a new message to be shown.
         * @param messageResourceId The new message, non-null.
         */
        void onNewMessage(@StringRes int messageResourceId);
    }
}
