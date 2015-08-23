package com.hkurokawa.qiitandroid.domain.user;

import com.hkurokawa.qiitandroid.domain.team.Team;

import java.util.Collection;
import java.util.List;

/**
 * Entity to represents a user.
 * Created by hiroshi on 8/16/15.
 */
public class User {
    private final String name;
    private final String profileImageUrl;
    private String token;

    public User(String name, String profileImageUrl, String token) {
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.token = token;
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

    public List<String> getAvailableTeams() {
        // FIXME
        return null;
    }
}
