package com.hkurokawa.qiitandroid.screens.main;

import com.applied_duality.rxmobile_android.AndroidSchedulers;
import com.hkurokawa.qiitandroid.domain.article.AnonymousArticle;
import com.hkurokawa.qiitandroid.domain.board.AnonymousArticlesBoard;
import com.hkurokawa.qiitandroid.domain.board.Board;
import com.hkurokawa.qiitandroid.domain.repository.NetworkAnonymousArticlesRepository;
import com.hkurokawa.qiitandroid.screens.Presenter;
import com.hkurokawa.qiitandroid.screens.Router;

import java.util.ArrayList;
import java.util.List;

/**
 * Presentation logic to display the latest anonymous articles.
 * Created by hiroshi on 15/08/19.
 */
public class AnonymousArticlesPresenter extends Presenter {
    private final Router router;
    private AnonymousArticlesScreen screen;
    private List<AnonymousArticle> articles;

    public AnonymousArticlesPresenter(Router router) {
        this.router = router;
        this.articles = new ArrayList<>();
    }

    public void takeView(AnonymousArticlesScreen screen) {
        this.screen = screen;
        final Board<AnonymousArticle> board = new AnonymousArticlesBoard(new NetworkAnonymousArticlesRepository());
        board.list(screen.getBottomHitObservable().startWith((Void) null))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        AnonymousArticlesPresenter.this::addArticles,
                        e -> AnonymousArticlesPresenter.this.screen.logErr(e, "Failed to list articles."),
                        AnonymousArticlesPresenter.this.screen::dismissBottomProgressBar);
    }

    private void addArticles(List<AnonymousArticle> articles) {
        this.articles.addAll(articles);
        this.publish();
    }

    private void publish() {
        this.screen.publish(this.articles);
    }

    public void onItemClick(AnonymousArticle article, int position) {
        this.router.toArticleScreen(this.screen, article.getTitle(), article.getBody());
    }

    public void onLoginMenuClick() {
        this.router.toLoginScreen(this.screen);
    }
}
