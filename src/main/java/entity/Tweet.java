package entity;

import org.eclipse.persistence.jpa.config.Cascade;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name = "TWEET")
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name="DATE_CREATED")
    private Date dateCreated;

    @Enumerated(EnumType.STRING)
    @Column(name="TWEET_TYPE")
    private TweetType type;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="USER_ID")
    private User owner;

    @Column(name="MESSAGE")
    private String message;

    public Tweet() {

    }

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
}