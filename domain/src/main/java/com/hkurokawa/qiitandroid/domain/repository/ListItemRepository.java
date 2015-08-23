package com.hkurokawa.qiitandroid.domain.repository;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Repository to list items.
 * Created by hiroshi on 8/16/15.
 */
public interface ListItemRepository<T> {
    /**
     * Returns a list of items on the specified page.
     * @param page the page to return the items on (starting from 0).
     * @return return <code>null</code> if there is no more items, the items on the specified page otherwise.
     */
    @Nullable
    List<T> list(int page);
}
