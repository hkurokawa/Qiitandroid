package com.hkurokawa.qiitandroid.domain.article;

/**
 * Value object to represent an author of an article.
 * Created by hiroshi on 8/22/15.
 */
public class Author {
    private final String name;
    private final String profileImageUrl;

    public Author(String name, String profileImageUrl) {
        this.name = name;
        this.profileImageUrl = profileImageUrl;
    }

    public String getName() {
        return name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }
}
