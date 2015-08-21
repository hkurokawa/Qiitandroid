package com.hkurokawa.qiitandroid.domain.repository;

import com.hkurokawa.qiitandroid.domain.article.Author;
import com.hkurokawa.qiitandroid.domain.article.UnbiasedArticle;
import com.hkurokawa.qiitandroid.network.QiitaApi;
import com.hkurokawa.qiitandroid.network.QiitaApiV1;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link ArticlesRepository}.
 * Created by hiroshi on 8/16/15.
 */
public class NetworkUnbiasedArticlesRepository implements ArticlesRepository<UnbiasedArticle> {
    private QiitaApiV1 api;

    public NetworkUnbiasedArticlesRepository() {
        this.api = QiitaApi.createV1();
    }

    @Override
    public List<UnbiasedArticle> list(int page) {
        final List<com.hkurokawa.qiitandroid.network.Article> items = this.api.items(page + 1);
        final List<UnbiasedArticle> articles = new ArrayList<>(items.size());
        for (com.hkurokawa.qiitandroid.network.Article i : items) {
            articles.add(convertToDomainModel(i));
        }
        return articles;
    }

    private static UnbiasedArticle convertToDomainModel(com.hkurokawa.qiitandroid.network.Article a) {
        return new UnbiasedArticle(a.getId(), convertToDomainModel(a.getUser()), a.getTitle(), a.getCreatedAt(), a.getCreatedAtInWords(), a.getUrl(), a.getBody());
    }

    private static Author convertToDomainModel(com.hkurokawa.qiitandroid.network.User u) {
        return new Author(u.getUrlName(), u.getProfileImageUrl());
    }
}
