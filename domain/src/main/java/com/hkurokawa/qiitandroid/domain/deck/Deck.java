package com.hkurokawa.qiitandroid.domain.deck;

import com.hkurokawa.qiitandroid.domain.article.AnonymousArticle;
import com.hkurokawa.qiitandroid.domain.article.PerceivedArticle;
import com.hkurokawa.qiitandroid.domain.repository.ListItemRepository;
import com.hkurokawa.qiitandroid.domain.team.Team;
import com.hkurokawa.qiitandroid.domain.user.User;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Deck represents an available set of {@link Team}, the currently selected {@link Team}.
 * Created by hiroshi on 8/23/15.
 */
public class Deck {
    private final List<Team> teams;
    private Team currentTeam;

    /**
     * Instantiate a Deck.
     * @throws NullPointerException if the provided <code>teams</code> is <code>null</code>.
     * @throws IllegalArgumentException if the provided <code>teams</code> is empty.
     * @param teams available teams.
     */
    public Deck(@NotNull List<Team> teams) {
        this.teams = teams;
        if (teams.isEmpty()) {
            throw new IllegalArgumentException("Empty teams not allowed.");
        }
        this.setCurrentTeam(teams.get(0));
    }

    public List<Team> getTeams() {
        return teams;
    }

    public Team getCurrentTeam() {
        return currentTeam;
    }

    public void setCurrentTeam(Team currentTeam) {
        this.currentTeam = currentTeam;
    }

    public static Deck getDefaultAnonymous(final ListItemRepository<AnonymousArticle> repository) {
        final Team t = Team.getDefaultAnonymous(repository);
        final List<Team> teams = Collections.singletonList(t);
        final Deck deck = new Deck(teams);
        return deck;
    }
}

