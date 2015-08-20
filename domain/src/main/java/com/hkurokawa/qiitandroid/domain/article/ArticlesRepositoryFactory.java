package com.hkurokawa.qiitandroid.domain.article;

import com.hkurokawa.qiitandroid.domain.repository.ArticlesRepository;
import com.hkurokawa.qiitandroid.domain.user.User;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A factory to instantiate articles repository.
 * Created by hiroshi on 8/21/15.
 */
public interface ArticlesRepositoryFactory {
    <T extends Article> ArticlesRepository<T> create(@Nullable String teamId, @Nullable String userToken, Class<T> clazz);
}
