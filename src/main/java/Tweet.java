import java.util.Date;

public class Tweet {

	private Date postDate;
	private TweetType type;
	private User owner;
	private String message;

	public Tweet(Date postDate, TweetType type, User owner, String message) {
		this.postDate = postDate;
		this.type = type;
		this.owner = owner;
		this.message = message;
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
}