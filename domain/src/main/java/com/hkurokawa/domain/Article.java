package com.hkurokawa.domain;

import java.util.Date;

/**
 * Value object to represent an article.
 * Created by hiroshi on 8/16/15.
 */
public class Article {
    private final long id;
    private final User user;
    private final String title;
    private final Date createdAt;
    private final String createdAtInWords;
    private final String url;
    private final String body;

    public Article(long id, User user, String title, Date createdAt, String createdAtInWords, String url, String body) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.createdAt = createdAt;
        this.createdAtInWords = createdAtInWords;
        this.url = url;
        this.body = body;
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
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
