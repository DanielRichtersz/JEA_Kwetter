package entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Tweet {

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

    public void addLike(User user) {
        this.likes.add(new Like(user, this, new Date()));
    }

    public void addLike(Like like) {
        this.likes.add(like);
    }
}