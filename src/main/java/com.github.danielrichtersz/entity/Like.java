package com.github.danielrichtersz.entity;

import java.io.Serializable;
import java.util.Date;

public class Like implements Serializable {

    private Long id;

    private long userId;

    private long tweetId;

    private Date dateLiked;

    public Like() {

    }

    public Like(long user, long tweet, Date dateLiked) {
        this.userId = user;
        this.tweetId = tweet;
        this.dateLiked = dateLiked;
    }

    public long getUserId() {
        return userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getTweetId() {
        return tweetId;
    }

    public void setTweetId(long tweet) {
        this.tweetId = tweet;
    }

    public Date getDateLiked() {
        return dateLiked;
    }

    public void setDateLiked(Date dateLiked) {
        this.dateLiked = dateLiked;
    }
}
