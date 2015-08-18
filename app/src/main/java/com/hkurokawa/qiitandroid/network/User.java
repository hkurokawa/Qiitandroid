package com.hkurokawa.qiitandroid.network;

import com.google.gson.annotations.SerializedName;

/**
 * User represents a JSON for user in Qiita API.
 * Created by hiroshi on 2015/06/09.
 */
public class User {
    @SerializedName("url_name")
    private String urlName;
    @SerializedName("profile_image_url")
    private String profileImageUrl;

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
