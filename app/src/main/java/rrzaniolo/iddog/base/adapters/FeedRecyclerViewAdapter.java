package rrzaniolo.iddog.base.adapters;

/*
 * Created by rrzaniolo on 02/05/18.
 * Copyright © 2018 rrzaniolo. All rights reserved.
 */

import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import rrzaniolo.iddog.LiveEvents.FeedImage;
import rrzaniolo.iddog.R;
import rrzaniolo.iddog.base.configurations.LottieViewConfiguration;
import rrzaniolo.iddog.databinding.ContentFeedBinding;

/**
 * A RecyclerViewAdapter for the FeedFragment.
 * */
public class FeedRecyclerViewAdapter extends RecyclerView.Adapter<FeedRecyclerViewAdapter.ViewHolder>{

    //region --- Variables ---
    private List<String> feedList;
    private FeedImage feedImage;
    //endregion

    //region --- Getters and Setters ---
    private List<String> getFeedList() {
        return feedList;
    }

    public void setFeedList(List<String> feedList) {
        this.feedList = feedList;
        notifyDataSetChanged();
    }

    private FeedImage getFeedImage() {
        return feedImage;
    }

    private void setFeedImage(FeedImage feedImage) {
        this.feedImage = feedImage;
    }

    //endregion

    //region --- Constructors ---
    public FeedRecyclerViewAdapter(List<String> feedList, FeedImage feedImage) {
        setFeedList(feedList);
        setFeedImage(feedImage);
    }
    //endregion

    //region --- RecyclerView Adapter ---
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.content_feed, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setShowLoading(true);
        Glide.with(holder.binding.getRoot().getContext())
                .load(getFeedList().get(position))
                .placeholder(R.drawable.ic_placeholder)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        holder.setShowLoading(false);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        holder.setShowLoading(false);
                        return false;
                    }
                })
                .into(holder.getBinding().cFeedIvPhoto);

        holder.getBinding().getRoot().setOnClickListener(v -> getFeedImage().setValue(getFeedList().get(position)));
    }

    @Override
    public int getItemCount() {
        return getFeedList().size();
    }
    //endregion

    //region --- ViewHolder ---
    /**
     * Simple ViewHolder class using DataBinding.
     * */
    public class ViewHolder extends RecyclerView.ViewHolder{
        private ContentFeedBinding binding;
        private ObservableBoolean showLoading = new ObservableBoolean(false);
        private LottieViewConfiguration configuration;

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

        public ContentFeedBinding getBinding() {
            return binding;
        }

        ViewHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(view);

            setConfiguration(new LottieViewConfiguration());
            getConfiguration().setAnimation(R.raw.loader);
            getConfiguration().setLoop(true);

            binding.setViewHolder(this);
        }
    }
    //endregion
}
