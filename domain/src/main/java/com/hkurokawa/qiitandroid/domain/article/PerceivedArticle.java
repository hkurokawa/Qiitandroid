package com.hkurokawa.qiitandroid.domain.article;

import java.util.Date;

/**
 * Aspects of an article from logged user's point of view.
 * Created by hiroshi on 8/21/15.
 */
public class PerceivedArticle extends AnonymousArticle {
    private boolean stocked;

    public PerceivedArticle(long id, Author author, String title, Date createdAt, String createdAtInWords, String url, String body, boolean stocked) {
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
