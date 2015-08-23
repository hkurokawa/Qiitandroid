package com.hkurokawa.qiitandroid.domain.board;

import com.hkurokawa.qiitandroid.domain.article.PerceivedArticle;
import com.hkurokawa.qiitandroid.domain.repository.ListItemRepository;

/**
 * Created by hiroshi on 8/23/15.
 */
public class PerceivedArticlesBoard extends Board<PerceivedArticle> {
    private final ListItemRepository<PerceivedArticle> repository;

    public PerceivedArticlesBoard(ListItemRepository<PerceivedArticle> repository) {
        this.repository = repository;
    }

    @Override
    public Class<PerceivedArticle> getItemClass() {
        return PerceivedArticle.class;
    }

    @Override
    protected ListItemRepository<PerceivedArticle> getRepository() {
        return this.repository;
    }
}
