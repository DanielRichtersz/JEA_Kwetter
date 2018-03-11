import java.util.Date;

public class Tweet {

    private Date postDate;

    private TweetType type;
    private User owner;
    private String message;

    public Tweet() {

    }

    public Date getPostDate() {
        return postDate;
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

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
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