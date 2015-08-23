package com.hkurokawa.qiitandroid.domain.board;

import com.hkurokawa.qiitandroid.domain.article.AnonymousArticle;
import com.hkurokawa.qiitandroid.domain.repository.ListItemRepository;

/**
 * Created by hiroshi on 8/23/15.
 */
public class AnonymousArticlesBoard extends Board<AnonymousArticle> {
    private final ListItemRepository repository;

    public AnonymousArticlesBoard(ListItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public Class<AnonymousArticle> getItemClass() {
        return AnonymousArticle.class;
    }

    @Override
    protected ListItemRepository getRepository() {
        return this.repository;
    }
}
