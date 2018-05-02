package rrzaniolo.iddog.base;

/*
 * Created by rrzaniolo on 02/05/18.
 * Copyright © 2018 rrzaniolo. All rights reserved.
 */

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import rrzaniolo.iddog.R;
import rrzaniolo.iddog.databinding.ContentFeedBinding;

public class CustomGridViewAdapter extends BaseAdapter{

    //region --- Variables ---
    private List<String> feedList;
    //endregion

    //region --- Getters and Setters ---
    public List<String> getFeedList() {
        return feedList;
    }

    private void setFeedList(List<String> feedList) {
        this.feedList = feedList;
    }
    //endregion

    //region --- Constructors ---
    public CustomGridViewAdapter(List<String> feedList) {
        setFeedList(feedList);
    }
    //endregion

    //region --- Base Adapter ---
    @Override
    public int getCount() {
        return getFeedList().size();
    }

    @Override
    public Object getItem(int position) {
        return getFeedList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        String url = getFeedList().get(position);
        ImageView imageView;

        if(convertView == null){
            ContentFeedBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(viewGroup.getContext()),
                    R.layout.content_feed,
                    viewGroup,
                    false
            );

            imageView = binding.cFeedIvPhoto;
            convertView = binding.getRoot();
        }else{

            imageView = convertView.findViewById(R.id.cFeed_ivPhoto);
        }

        Glide.with(viewGroup.getContext())
                .load(url)
                .into(imageView);

        return convertView;
    }
    //endregion
}
