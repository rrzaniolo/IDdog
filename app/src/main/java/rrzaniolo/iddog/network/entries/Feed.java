package rrzaniolo.iddog.network.entries;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Rodrigo Rodrigues Zaniolo on 4/29/2018.
 * All rights reserved.
 */
public class Feed {

    @SerializedName("category")
    private String category;

    @SerializedName("list")
    private List<String> photos;

    public Feed() { }

    public Feed(String category, List<String> photos) {
        this.category = category;
        this.photos = photos;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }
}
