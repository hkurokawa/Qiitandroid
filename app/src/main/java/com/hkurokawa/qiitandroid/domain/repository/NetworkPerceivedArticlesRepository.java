package com.hkurokawa.qiitandroid.domain.repository;

import com.hkurokawa.qiitandroid.domain.article.Author;
import com.hkurokawa.qiitandroid.domain.article.PerceivedArticle;
import com.hkurokawa.qiitandroid.network.QiitaApi;
import com.hkurokawa.qiitandroid.network.QiitaApiV1;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link ListItemRepository} for {@link PerceivedArticle}.
 * Created by hiroshi on 8/23/15.
 */
public class NetworkPerceivedArticlesRepository implements ListItemRepository<PerceivedArticle> {
    private QiitaApiV1 api;

    public NetworkPerceivedArticlesRepository() {
        this.api = QiitaApi.createV1();
    }

    @Nullable
    @Override
    public List<PerceivedArticle> list(int page) {
        final List<com.hkurokawa.qiitandroid.network.Article> items = this.api.items(page + 1);
        final List<PerceivedArticle> articles = new ArrayList<>(items.size());
        for (com.hkurokawa.qiitandroid.network.Article i : items) {
            articles.add(convertToDomainModel(i));
        }
        return articles;
    }

    private static PerceivedArticle convertToDomainModel(com.hkurokawa.qiitandroid.network.Article a) {
        return new PerceivedArticle(a.getId(), convertToDomainModel(a.getUser()), a.getTitle(), a.getCreatedAt(), a.getCreatedAtInWords(), a.getUrl(), a.getBody(), a.isStocked());
    }

    private static Author convertToDomainModel(com.hkurokawa.qiitandroid.network.User u) {
        return new Author(u.getUrlName(), u.getProfileImageUrl());
    }
}
