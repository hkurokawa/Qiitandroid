package com.hkurokawa.qiitandroid.domain.user;

/**
 * Entity to represents a user.
 * Created by hiroshi on 8/16/15.
 */
public class User {
    private final String name;
    private final String profileImageUrl;
    private String token;

    public User(String name, String profileImageUrl) {
        this.name = name;
        this.profileImageUrl = profileImageUrl;
    }

    public String getName() {
        return name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
