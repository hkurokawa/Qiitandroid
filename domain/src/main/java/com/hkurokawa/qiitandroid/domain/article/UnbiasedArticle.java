package com.hkurokawa.qiitandroid.domain.article;

import com.hkurokawa.qiitandroid.domain.user.User;

import java.util.Date;

/**
 * Value object of an article from anonymous point of view.
 * Created by hiroshi on 8/16/15.
 */
public class UnbiasedArticle extends Article {
    public UnbiasedArticle(long id, Author author, String title, Date createdAt, String createdAtInWords, String url, String body) {
        super(id, author, title, createdAt, createdAtInWords, url, body);
    }
}
