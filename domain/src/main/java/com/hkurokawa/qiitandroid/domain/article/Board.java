package com.hkurokawa.qiitandroid.domain.article;

import com.hkurokawa.qiitandroid.domain.repository.ArticlesRepository;
import com.hkurokawa.qiitandroid.domain.team.Team;
import com.hkurokawa.qiitandroid.domain.user.User;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.schedulers.NewThreadScheduler;

/**
 * Entity to represent a collection of articles either associated with a team or public. It is identified with a combination of <code>user</code> and <code>team</code>.
 * Created by hiroshi on 8/21/15.
 */
public class Board<T extends Article> {
    private final ArticlesRepository<T> repository;
    @Nullable
    private final User user;
    @Nullable
    private final Team team;

    public Board(ArticlesRepositoryFactory factory, Class<T> clazz) {
        this(null, null, factory, clazz);
    }

    public Board(@Nullable Team team, @Nullable User user, ArticlesRepositoryFactory factory, Class<T> clazz) {
        this.user = user;
        this.team = team;
        this.repository = factory.create(team == null ? null : team.getId(), user == null ? null : user.getToken(), clazz);
    }

    public Team getTeam() {
        return team;
    }

    public User getUser() {
        return user;
    }

    public boolean isPublic() {
        return this.team == null;
    }

    public boolean isBiased() {
        return this.user != null;
    }

    /**
     * Returns an observable to observer a list of a list of articles.
     * The observable must returns {@link List< Article >} on {@link Observer#onNext(Object)} while
     * there are any articles to list and must call {@link Observer#onCompleted()} when no more
     * articles to show.
     *
     * @param trigger an {@link Observable} to trigger the loading of the next list of articles to return.
     * @return an {@link Observable} to observer a list of a list of articles.
     */
    public Observable<List<T>> list(final Observable<Void> trigger) {
        return Observable.create(subscriber -> {
                    trigger.observeOn(new NewThreadScheduler()).subscribe(new Observer<Void>() {
                        private int page;

                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (!subscriber.isUnsubscribed()) {
                                subscriber.onError(e);
                            }
                        }

                        @Override
                        public void onNext(Void aVoid) {
                            if (!subscriber.isUnsubscribed()) {
                                final List<T> articles = Board.this.repository.list(this.page);
                                if (articles == null) {
                                    subscriber.onCompleted();
                                } else {
                                    this.page++;
                                    subscriber.onNext(articles);
                                }
                            }
                        }
                    });
                }
        );
    }
}