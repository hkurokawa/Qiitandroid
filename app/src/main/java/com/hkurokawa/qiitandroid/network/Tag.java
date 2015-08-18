package com.hkurokawa.qiitandroid.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Tag represents a tag in JSON for Qiita API v1.
 * Created by hiroshi on 2015/06/09.
 */
public class Tag {
    private String name;
    @SerializedName("url_name")
    private String urlName;
    @SerializedName("icon_url")
    private String iconUrl;
    private List<String> versions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public List<String> getVersions() {
        return versions;
    }

    public void setVersions(List<String> versions) {
        this.versions = versions;
    }
}
