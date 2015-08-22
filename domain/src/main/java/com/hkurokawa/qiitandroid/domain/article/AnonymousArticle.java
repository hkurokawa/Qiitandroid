package com.hkurokawa.qiitandroid.domain.article;

/**
 * Aspects of an article from anonymous user's point of view.
 * Created by hiroshi on 8/21/15.
 */

import java.util.Date;

public class AnonymousArticle {
    private final long id;
    private final Author author;
    private final String title;
    private final Date createdAt;
    private final String createdAtInWords;
    private final String url;
    private final String body;

    public AnonymousArticle(long id, Author author, String title, Date createdAt, String createdAtInWords, String url, String body) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.createdAt = createdAt;
        this.createdAtInWords = createdAtInWords;
        this.url = url;
        this.body = body;
    }

    public long getId() {
        return id;
    }

    public Author getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getCreatedAtInWords() {
        return createdAtInWords;
    }

    public String getUrl() {
        return url;
    }

    public String getBody() {
        return body;
    }
}
