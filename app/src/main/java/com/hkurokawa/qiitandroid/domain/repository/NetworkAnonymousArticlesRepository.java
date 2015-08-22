package com.hkurokawa.qiitandroid.domain.repository;

import com.hkurokawa.qiitandroid.domain.article.AnonymousArticle;
import com.hkurokawa.qiitandroid.domain.article.Author;
import com.hkurokawa.qiitandroid.network.QiitaApi;
import com.hkurokawa.qiitandroid.network.QiitaApiV1;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link Repository}.
 * Created by hiroshi on 8/16/15.
 */
public class NetworkAnonymousArticlesRepository implements Repository<AnonymousArticle> {
    private QiitaApiV1 api;

    public NetworkAnonymousArticlesRepository() {
        this.api = QiitaApi.createV1();
    }

    @Override
    public List<AnonymousArticle> list(int page) {
        final List<com.hkurokawa.qiitandroid.network.Article> items = this.api.items(page + 1);
        final List<AnonymousArticle> articles = new ArrayList<>(items.size());
        for (com.hkurokawa.qiitandroid.network.Article i : items) {
            articles.add(convertToDomainModel(i));
        }
        return articles;
    }

    private static AnonymousArticle convertToDomainModel(com.hkurokawa.qiitandroid.network.Article a) {
        return new AnonymousArticle(a.getId(), convertToDomainModel(a.getUser()), a.getTitle(), a.getCreatedAt(), a.getCreatedAtInWords(), a.getUrl(), a.getBody());
    }

    private static Author convertToDomainModel(com.hkurokawa.qiitandroid.network.User u) {
        return new Author(u.getUrlName(), u.getProfileImageUrl());
    }
}
