package com.hkurokawa.qiitandroid.domain.board;

import com.hkurokawa.qiitandroid.domain.article.AnonymousArticle;
import com.hkurokawa.qiitandroid.domain.repository.Repository;

/**
 * Created by hiroshi on 8/23/15.
 */
public class AnonymousArticlesBoard extends Board<AnonymousArticle> {
    private final Repository repository;

    public AnonymousArticlesBoard(Repository repository) {
        this.repository = repository;
    }

    @Override
    protected Repository getRepository() {
        return this.repository;
    }
}
