package com.hkurokawa.qiitandroid.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Article represents a single Qiita article.
 * Created by hiroshi on 2015/06/09.
 */
public class Article {
    private Number id;
    private String uuid;
    private User user;
    private String title;
    @SerializedName("created_at")
    private Date createdAt;
    @SerializedName("updated_at")
    private Date updatedAt;
    @SerializedName("created_at_in_words")
    private String createdAtInWords;
    @SerializedName("updated_at_in_words")
    private String updatedAtInWords;
    private List<Tag> tags;
    @SerializedName("stock_count")
    private int stockCount;
    @SerializedName("comment_count")
    private int commentCount;
    private String url;
    @SerializedName("created_at_as_seconds")
    private long createdAtAsSeconds;
    private boolean tweet;
    @SerializedName("gist_url")
    private String gistUrl;
    @SerializedName("private")
    private boolean inPrivate;
    private boolean stocked;
    @SerializedName("raw_body")
    private String rawBody;
    private String body;
    @SerializedName("stock_users")
    private List<String> stockUsers;

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAtInWords() {
        return createdAtInWords;
    }

    public void setCreatedAtInWords(String createdAtInWords) {
        this.createdAtInWords = createdAtInWords;
    }

    public String getUpdatedAtInWords() {
        return updatedAtInWords;
    }

    public void setUpdatedAtInWords(String updatedAtInWords) {
        this.updatedAtInWords = updatedAtInWords;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public int getStockCount() {
        return stockCount;
    }

    public void setStockCount(int stockCount) {
        this.stockCount = stockCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getCreatedAtAsSeconds() {
        return createdAtAsSeconds;
    }

    public void setCreatedAtAsSeconds(long createdAtAsSeconds) {
        this.createdAtAsSeconds = createdAtAsSeconds;
    }

    public boolean isTweet() {
        return tweet;
    }

    public void setTweet(boolean tweet) {
        this.tweet = tweet;
    }

    public String getGistUrl() {
        return gistUrl;
    }

    public void setGistUrl(String gistUrl) {
        this.gistUrl = gistUrl;
    }

    public boolean isInPrivate() {
        return inPrivate;
    }

    public void setInPrivate(boolean inPrivate) {
        this.inPrivate = inPrivate;
    }

    public boolean isStocked() {
        return stocked;
    }

    public void setStocked(boolean stocked) {
        this.stocked = stocked;
    }

    public String getRawBody() {
        return rawBody;
    }

    public void setRawBody(String rawBody) {
        this.rawBody = rawBody;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<String> getStockUsers() {
        return stockUsers;
    }

    public void setStockUsers(List<String> stockUsers) {
        this.stockUsers = stockUsers;
    }

    @Override
    public String toString() {
        return this.title;
    }
}
