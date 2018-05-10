package com.github.danielrichtersz.entity;

import java.io.Serializable;
import java.util.*;

public class Tweet implements Serializable {

    private Long id;

    private Date dateCreated;

    private TweetType type;

    private User owner;

    private List<Like> likes;

    private String message;

    public Tweet() {
        likes = new ArrayList<>();
    }

    //region Getters & Setters
    public Date getDateCreated() {
        return dateCreated;
    }

    public TweetType getType() {
        return type;
    }

    public User getOwner() {
        return owner;
    }

    public String getMessage() {
        return message;
    }

    public void setDateCreated(Date postDate) {
        this.dateCreated = postDate;
    }

    public void setType(TweetType type) {
        this.type = type;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }
    //endregion

    public Like addLike(long userId) {

        //Todo: +2 hours timezone
        TimeZone timeZone = TimeZone.getTimeZone("Europe/Amsterdam");
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeZone(timeZone);
        Date date = calendar.getTime();

        Like like = new Like(userId, this.getId(), date);
        //Set the id of the like. This is a far from perfect (or programming wise, pretty) solution
        //however it fits the needs and will function as needed
        like.setId((long) this.getLikes().size() + 1);
        this.likes.add(like);
        return like;
    }

    public void addLike(Like like) {
        this.likes.add(like);
    }
}