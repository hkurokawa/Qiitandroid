package com.hkurokawa.qiitandroid.domain.article;

import com.hkurokawa.qiitandroid.domain.user.User;

import java.util.Date;

/**
 * An article entity from logged user's point of view.
 * Created by hiroshi on 8/21/15.
 */
public class BiasedArticle extends Article {
    private boolean stocked;

    public BiasedArticle(long id, Author author, String title, Date createdAt, String createdAtInWords, String url, String body, boolean stocked) {
        super(id, author, title, createdAt, createdAtInWords, url, body);
        this.stocked = stocked;
    }

    public boolean isStocked() {
        return stocked;
    }

    public void setStocked(boolean stocked) {
        this.stocked = stocked;
    }
}
