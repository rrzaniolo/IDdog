package rrzaniolo.iddog.LiveEvents;

/*
  Created by Rodrigo Rodrigues Zaniolo on 4/28/2018.
  All rights reserved.
 */

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;

/**
 * A rrzaniolo.iddog.LiveEvents.SingleLiveEvent used for displaying a FeedImage.
 * Like a {@link SingleLiveEvent} but also prevents
 * null images and uses a custom observer.
 * <p>
 * Note that only one observer is going to be notified of changes.
 */
public class FeedImage extends SingleLiveEvent<String> {
    public void observe(LifecycleOwner owner, final FeedImageObserver observer) {
        super.observe(owner, url -> {
            if (url == null) {
                return;
            }
            observer.onShow(url);
        });
    }

    public interface FeedImageObserver {
        /**
         * Called when there is a image to show.
         * @param url The image url, non-null.
         */
        void onShow(@NonNull String url);
    }
}
