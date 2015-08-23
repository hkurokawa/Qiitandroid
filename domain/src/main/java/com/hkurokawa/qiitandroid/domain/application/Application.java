package com.hkurokawa.qiitandroid.domain.application;

import com.hkurokawa.qiitandroid.domain.article.AnonymousArticle;
import com.hkurokawa.qiitandroid.domain.article.PerceivedArticle;
import com.hkurokawa.qiitandroid.domain.deck.Deck;
import com.hkurokawa.qiitandroid.domain.repository.ApplicationRepository;
import com.hkurokawa.qiitandroid.domain.repository.ListItemRepository;
import com.hkurokawa.qiitandroid.domain.team.Team;
import com.hkurokawa.qiitandroid.domain.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity to represent an application status. Single instance on the process.
 * Created by hiroshi on 8/16/15.
 */
public class Application {
    private final ApplicationRepository appRepository;
    private final ListItemRepository<AnonymousArticle> aRepository;
    private final ListItemRepository<PerceivedArticle> pRepository;
    private User user;
    private Deck deck;

    public Application(ApplicationRepository appRepository, ListItemRepository<AnonymousArticle> aRepository, ListItemRepository<PerceivedArticle> pRepository) {
        this.appRepository = appRepository;
        this.aRepository = aRepository;
        this.pRepository = pRepository;
    }

    private void init() {
        this.setUser(this.appRepository.loadUser());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        this.updateDeck(user);
    }

    private void updateDeck(User user) {
        if (user == null) {
            this.deck = Deck.getDefaultAnonymous(this.aRepository);
        } else {
            final Team t = Team.getDefaultPerceived(this.pRepository);
            final List<Team> teams = new ArrayList<Team>();
            teams.add(t);
            for (String id : user.getAvailableTeams()) {
                // FIXME
            }
            this.deck = new Deck(teams);
        }
    }

    public Deck getDeck() {
        if (this.deck == null) {
            this.init();
        }
        return this.deck;
    }

    public boolean isAnonymous() {
        return this.user == null;
    }
}
