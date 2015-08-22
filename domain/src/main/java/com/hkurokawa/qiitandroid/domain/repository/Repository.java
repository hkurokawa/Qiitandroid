package com.hkurokawa.qiitandroid.domain.repository;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Repository to list entities.
 * Created by hiroshi on 8/16/15.
 */
public interface Repository<T> {
    /**
     * Returns a list of entities on the specified page.
     * @param page the page to return the entities on (starting from 0).
     * @return return <code>null</code> if there is no more entities, the entities on the specified page otherwise.
     */
    @Nullable
    List<T> list(int page);
}
