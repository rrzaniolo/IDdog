package rrzaniolo.iddog.network.entries;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/*
 * Created by Rodrigo Rodrigues Zaniolo on 4/29/2018.
 * All rights reserved.
 */

/**
 * Model for the User.
 */
@SuppressWarnings("unused")
public class User {

    @SerializedName("_id")
    private String id;

    @SerializedName("email")
    private String email;

    @SerializedName("token")
    private String token;

    @SerializedName("createdAt")
    private Date created;

    @SerializedName("updatedAt")
    private Date updated;

    @SerializedName("__v")
    private Integer version;


    public User() { }

    public User(String id, String email, String token, Date created, Date updated, Integer version) {
        this.id = id;
        this.email = email;
        this.token = token;
        this.created = created;
        this.updated = updated;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
