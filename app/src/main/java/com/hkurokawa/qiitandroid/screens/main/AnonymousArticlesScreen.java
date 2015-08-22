package com.hkurokawa.qiitandroid.screens.main;

import com.hkurokawa.qiitandroid.domain.article.AnonymousArticle;
import com.hkurokawa.qiitandroid.screens.Screen;

import java.util.List;

import rx.Observable;

/**
 * A screen to display the latest articles.
 * Created by hiroshi on 15/08/19.
 */
public interface AnonymousArticlesScreen extends Screen {
    void publish(List<AnonymousArticle> articles);
    Observable<Void> getBottomHitObservable();
    void dismissBottomProgressBar();
    void logErr(Throwable e, String msg);
}
