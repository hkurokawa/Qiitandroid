package com.hkurokawa.qiitandroid.domain.application;

import com.hkurokawa.qiitandroid.domain.board.Board;
import com.hkurokawa.qiitandroid.domain.team.Team;
import com.hkurokawa.qiitandroid.domain.user.User;

import java.util.List;

/**
 * Entity to represent an application status. Single instance on the process.
 * Created by hiroshi on 8/16/15.
 */
public class Application {
    private User user;
    private Team team;

    public boolean isAnonymous() {
        return this.user == null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Board<?>> getBoards() {
        return this.team.getBoards();
    }
}
