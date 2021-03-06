package com.hkurokawa.qiitandroid.domain.team;

import com.hkurokawa.qiitandroid.domain.article.AnonymousArticle;
import com.hkurokawa.qiitandroid.domain.article.PerceivedArticle;
import com.hkurokawa.qiitandroid.domain.board.AnonymousArticlesBoard;
import com.hkurokawa.qiitandroid.domain.board.Board;
import com.hkurokawa.qiitandroid.domain.board.PerceivedArticlesBoard;
import com.hkurokawa.qiitandroid.domain.repository.ListItemRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Entity to represent a team.
 * Created by hiroshi on 8/21/15.
 */
public class Team {
    private final String id;
    private final String name;
    private final boolean active;
    private List<Board<?>> boards;

    public Team(String id, String name, boolean active) {
        this.id = id;
        this.name = name;
        this.active = active;
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

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public static Team getDefaultAnonymous(final ListItemRepository<AnonymousArticle> repository) {
        final Board<AnonymousArticle> b = new AnonymousArticlesBoard(repository);
        final Team t = new Team(null, "Qiita", true);
        t.setBoards(Collections.<Board<?>>singletonList(b));
        return t;
    }

    public static Team getDefaultPerceived(final ListItemRepository<PerceivedArticle> repository) {
        final Board<PerceivedArticle> b = new PerceivedArticlesBoard(repository);
        final Team t = new Team(null, "Qiita", true);
        final List<Board<?>> boards = new ArrayList<Board<?>>();
        boards.add(b);
        t.setBoards(boards);
        return t;
    }
}
