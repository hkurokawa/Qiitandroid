package com.hkurokawa.qiitandroid.domain.repository;

import com.hkurokawa.domain.Article;
import com.hkurokawa.domain.ArticlesListService;
import com.hkurokawa.domain.ArticlesRepository;
import com.hkurokawa.domain.User;
import com.hkurokawa.qiitandroid.QiitaApi;
import com.hkurokawa.qiitandroid.network.QiitaApiV1;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link ArticlesRepository}.
 * Created by hiroshi on 8/16/15.
 */
public class NetworkArticlesRepository implements ArticlesRepository {
    private QiitaApiV1 api;

    public NetworkArticlesRepository() {
        this.api = QiitaApi.createV1();
    }

    @Override
    public List<Article> list(int page) {
        final List<com.hkurokawa.qiitandroid.network.Article> items = this.api.items(page);
        final List<Article> articles = new ArrayList<>(items.size());
        for (com.hkurokawa.qiitandroid.network.Article i : items) {
            articles.add(convertToDomainModel(i));
        }
        return articles;
    }

    private static Article convertToDomainModel(com.hkurokawa.qiitandroid.network.Article a) {
        final Article article = new Article(a.getId(), convertToDomainModel(a.getUser()), a.getTitle(), a.getCreatedAt(), a.getCreatedAtInWords(), a.getUrl(), a.getBody());
        return article;
    }

    private static User convertToDomainModel(com.hkurokawa.qiitandroid.network.User u) {
        return new User(u.getUrlName(), u.getProfileImageUrl());
    }
}
