package com.hkurokawa.qiitandroid.screens.main;

import com.hkurokawa.qiitandroid.domain.article.Article;
import com.hkurokawa.qiitandroid.domain.article.ArticlesListService;
import com.hkurokawa.qiitandroid.domain.repository.ArticlesRepository;
import com.hkurokawa.qiitandroid.screens.Presenter;
import com.hkurokawa.qiitandroid.screens.Router;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Presentation logic to display the latest articles.
 * Created by hiroshi on 15/08/19.
 */
public class LatestArticlesPresenter extends Presenter {
    private final Router router;
    private final ArticlesRepository repository;
    private LatestArticlesScreen screen;
    private List<Article> articles;

    public LatestArticlesPresenter(Router router, ArticlesRepository repository) {
        this.router = router;
        this.repository = repository;
        this.articles = new ArrayList<>();
    }

    public void takeView(LatestArticlesScreen screen) {
        this.screen = screen;
        final ArticlesListService service = new ArticlesListService(this.repository);
        service.list(screen.getBottomHitObservable().startWith((Void) null))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Article>>() {
                    @Override
                    public void onCompleted() {
                        LatestArticlesPresenter.this.screen.dismissBottomProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LatestArticlesPresenter.this.screen.logErr(e, "Failed to list articles.");
                    }

                    @Override
                    public void onNext(List<Article> articles) {
                        LatestArticlesPresenter.this.addArticles(articles);
                    }
                });
    }

    private void addArticles(List<Article> articles) {
        this.articles.addAll(articles);
        this.publish();
    }

    private void publish() {
        this.screen.publish(this.articles);
    }

    public void onItemClick(Article article, int position) {
        this.router.toArticleScreen(this.screen, article.getTitle(), article.getBody());
    }

    public void onLoginMenuClick() {
        this.router.toLoginScreen(this.screen);
    }
}
