package com.hkurokawa.qiitandroid.domain.board;

import com.hkurokawa.qiitandroid.domain.repository.Repository;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.schedulers.NewThreadScheduler;

/**
 * Entity to represent a collection of entities.
 * Created by hiroshi on 8/21/15.
 */
public abstract class Board<T> {
    /**
     * Returns an observable with which an emit of a list of entities can be observed.
     * The observable must returns a list of entities of type <code>T</code> on {@link Observer#onNext(Object)} while
     * there are any entities to list and must call {@link Observer#onCompleted()} when there is sno more
     * entities to show.
     *
     * @param trigger an {@link Observable} to trigger the loading of the next list of entities to return.
     * @return an {@link Observable} to observe emits of a list of entities.
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
                                final List<T> articles = Board.this.getRepository().list(this.page);
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

    protected abstract Repository<T> getRepository();
}