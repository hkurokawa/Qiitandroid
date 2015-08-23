package com.hkurokawa.qiitandroid.domain.user;

import com.hkurokawa.qiitandroid.domain.team.Team;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Entity to represents a user.
 * Created by hiroshi on 8/16/15.
 */
public class User {
    private final String name;
    private final String profileImageUrl;
    private final List<Team> availableTeams;
    private String token;

    public User(String name, String profileImageUrl, String token) {
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.token = token;
        this.availableTeams = new ArrayList<Team>();
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

    public void setTeams(List<Team> teams) {
        for (Team t : teams) {
            if (t.isActive()) {
                this.availableTeams.add(t);
            }
        }
    }

    public List<Team> getAvailableTeams() {
        return this.availableTeams;
    }
}
