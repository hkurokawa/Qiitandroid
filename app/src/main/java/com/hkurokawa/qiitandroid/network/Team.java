package com.hkurokawa.qiitandroid.network;

/**
 * Team represents information about team.
 * Created by hiroshi on 8/23/15.
 */
public class Team {
    private String id;
    private String name;
    private boolean active;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
