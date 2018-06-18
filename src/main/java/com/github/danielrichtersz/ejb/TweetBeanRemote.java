package ejb;

import entity.Like;
import entity.Tweet;

import javax.management.InstanceAlreadyExistsException;
import java.text.ParseException;
import java.util.List;

public interface TweetBeanRemote {
    Tweet createTweet(long userId, String dateCreated, String message) throws ParseException;

    Tweet getTweetByTweetID(long tweetId);

    List<Tweet> getTweetsByUserID(long userId);

    List<Tweet> getTweetsByUserIDBetweenDates(String startdate, String enddate, long userId);

    void removeTweet(long tweetId, long userId);

    Like addLikeToTweet(long tweetId, long userId) throws InstanceAlreadyExistsException;

    void removeLikeFromTweet(long tweetId, long userId);
}
