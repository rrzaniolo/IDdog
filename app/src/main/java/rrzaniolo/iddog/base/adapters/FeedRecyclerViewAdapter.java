package rrzaniolo.iddog.base.adapters;

/*
 * Created by rrzaniolo on 02/05/18.
 * Copyright © 2018 rrzaniolo. All rights reserved.
 */

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import rrzaniolo.iddog.LiveEvents.FeedImage;
import rrzaniolo.iddog.R;
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
        Glide.with(holder.binding.getRoot().getContext())
                .load(getFeedList().get(position))
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
    class ViewHolder extends RecyclerView.ViewHolder{
        private ContentFeedBinding binding;

        public ContentFeedBinding getBinding() {
            return binding;
        }

        ViewHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(view);
        }
    }
    //endregion
}
