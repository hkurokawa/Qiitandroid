package com.hkurokawa.qiitandroid.domain.team;

/**
 * Value object to represent a team.
 * Created by hiroshi on 8/21/15.
 */
public class Team {
    private final String id;

    public Team(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
