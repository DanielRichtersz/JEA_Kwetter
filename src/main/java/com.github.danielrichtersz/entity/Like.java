package com.github.danielrichtersz.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Like implements Serializable {

    private long id;

    private long userId;

    private long tweetId;

    private Date dateLiked;

    public Like() {

    }

    public Like(long user, long tweet) {
        this.userId = user;
        this.tweetId = tweet;

        //Set dateLiked
        TimeZone timeZone = TimeZone.getTimeZone("Europe/Amsterdam");
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeZone(timeZone);
        this.dateLiked = calendar.getTime();
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
