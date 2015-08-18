package com.hkurokawa.domain;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import rx.Observable;
import rx.Subscriber;

/**
 * A service to list articles.
 * Created by hiroshi on 8/16/15.
 */
public class ArticlesListService {
    private ArticlesRepository repository;
    private Observable<List<Article>> observable;
    private Subscriber<? super List<Article>> subscriber;
    private AtomicInteger page = new AtomicInteger();
    private AtomicBoolean loading = new AtomicBoolean();

    public ArticlesListService(ArticlesRepository repository) {
        this.repository = repository;
        this.observable = Observable.create(new Observable.OnSubscribe<List<Article>>() {
            @Override
            public void call(Subscriber<? super List<Article>> subscriber) {
                ArticlesListService.this.subscriber = subscriber;
                ArticlesListService.this.next();
            }
        });
    }

    public Observable<List<Article>> source() {
        return this.observable;
    }

    public void next() {
        if (this.subscriber != null && !this.subscriber.isUnsubscribed() && this.loading.compareAndSet(false, true)) {
            try {
                final List<Article> articles = this.repository.list(this.page.incrementAndGet());
                if (articles == null) {
                    this.subscriber.onCompleted();
                } else {
                    this.subscriber.onNext(articles);
                }
            } catch (Exception e) {
                this.subscriber.onError(e);
            } finally {
                this.loading.set(false);
            }
        }
    }
}
