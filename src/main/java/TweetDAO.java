import java.util.List;

public interface TweetDAO {

    Tweet getTweet();
    List<Tweet> getTweets();
    boolean insertTweet();
    boolean updateTweet();
    boolean deleteTweet();
}
