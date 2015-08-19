package com.hkurokawa.qiitandroid.domain.article;

import com.hkurokawa.qiitandroid.domain.repository.ArticlesRepository;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * A service to list articles.
 * Created by hiroshi on 8/16/15.
 */
public class ArticlesListService {
    private ArticlesRepository repository;

    public ArticlesListService(ArticlesRepository repository) {
        this.repository = repository;
    }

    /**
     * Returns an observable to observer a list of a list of articles.
     * The observable must returns {@link List< Article >} on {@link Subscriber#onNext(Object)} while
     * there are any articles to list and must call {@link Subscriber#onCompleted()} when no more
     * articles to show.
     * @param trigger an {@link Observable} to trigger the loading of the next list of articles to return.
     * @return an {@link Observable} to observer a list of a list of articles.
     */
    public Observable<List<Article>> list(final Observable<Void> trigger) {
        return Observable.create(new Observable.OnSubscribe<List<Article>>() {
            @Override
            public void call(final Subscriber<? super List<Article>> subscriber) {
                trigger.observeOn(Schedulers.newThread()).subscribe(new Subscriber<Void>() {
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
                            final List<Article> articles = ArticlesListService.this.repository.list(this.page);
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
        });
    }
}
