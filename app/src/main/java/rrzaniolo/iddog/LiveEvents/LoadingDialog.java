package rrzaniolo.iddog.LiveEvents;

/*
  Created by Rodrigo Rodrigues Zaniolo on 4/28/2018.
  All rights reserved.
 */

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * A rrzaniolo.iddog.LiveEvents.SingleLiveEvent used for displaying a LoadingDialog (or animation).
 * Like a {@link SingleLiveEvent} but also prevents
 * null states and uses a custom observer.
 * <p>
 * Note that only one observer is going to be notified of changes.
 */
public class LoadingDialog extends SingleLiveEvent<Boolean> {
    public void observe(LifecycleOwner owner, final LoadingDialogObserver observer) {
        super.observe(owner, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isVisible) {
                if (isVisible == null) {
                    return;
                }
                observer.onVisibilityChange(isVisible);
            }
        });
    }

    public interface LoadingDialogObserver {
        /**
         * Called when there is a change on LoadingDialog visibility.
         * @param isVisible The new visibility, non-null.
         */
        void onVisibilityChange(@NonNull Boolean isVisible);
    }
}
