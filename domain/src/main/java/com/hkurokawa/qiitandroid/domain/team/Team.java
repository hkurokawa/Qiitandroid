package com.hkurokawa.qiitandroid.domain.team;

import com.hkurokawa.qiitandroid.domain.board.Board;

import java.util.List;

/**
 * Entity to represent a team.
 * Created by hiroshi on 8/21/15.
 */
public class Team {
    private final String id;

    private List<Board<?>> boards;

    public Team(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public List<Board<?>> getBoards() {
        return boards;
    }

    public void setBoards(List<Board<?>> boards) {
        this.boards = boards;
    }
}
