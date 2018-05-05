package entity;

import java.io.Serializable;
import java.util.Date;

public class Like implements Serializable {

    private Long id;

    private User user;

    private Tweet tweet;

    private Date dateLiked;

    public Like() {

    }

    public Like(User user, Tweet tweet, Date dateLiked) {
        this.user = user;
        this.tweet = tweet;
        this.dateLiked = dateLiked;
    }

    public User getUser() {
        return user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
