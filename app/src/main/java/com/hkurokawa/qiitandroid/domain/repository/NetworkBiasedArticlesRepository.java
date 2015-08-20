package com.hkurokawa.qiitandroid.domain.repository;

import com.hkurokawa.qiitandroid.domain.article.BiasedArticle;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Implementation of {@link ArticlesRepository}.
 * Created by hiroshi on 8/21/15.
 */
public class NetworkBiasedArticlesRepository implements ArticlesRepository<BiasedArticle> {
    @Nullable
    @Override
    public List<BiasedArticle> list(int page) {
        // FIXME stub
        return null;
    }
}
