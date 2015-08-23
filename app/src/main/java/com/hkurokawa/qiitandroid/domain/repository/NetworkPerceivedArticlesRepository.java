package com.hkurokawa.qiitandroid.domain.repository;

import com.hkurokawa.qiitandroid.domain.article.PerceivedArticle;
import com.hkurokawa.qiitandroid.network.QiitaApi;
import com.hkurokawa.qiitandroid.network.QiitaApiV1;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
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
        // FIXME
        return null;
    }
}
