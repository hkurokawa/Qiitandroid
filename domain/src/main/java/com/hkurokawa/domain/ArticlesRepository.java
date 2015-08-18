package com.hkurokawa.domain;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Repository to list articles.
 * Created by hiroshi on 8/16/15.
 */
public interface ArticlesRepository {
    /**
     * Returns a list of articles on the specified page.
     * @param page the page to return the articles on
     * @return return <code>null</code> if there is no more articles, the articles on the specified page otherwise.
     */
    @Nullable
    List<Article> list(int page);
}
