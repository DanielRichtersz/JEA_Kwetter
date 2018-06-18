package dao;

import entity.Like;
import entity.Tweet;
import java.util.Date;
import java.util.List;
import javax.management.InstanceAlreadyExistsException;

public interface TweetDAO extends BaseDAO<Tweet> {
    Like addLikeToTweet(long tweetId, long userId) throws InstanceAlreadyExistsException;
    void removeLikeFromTweet(long tweetId, long userId);

    long getNewTweetID();

    List<Tweet> getTweetsByUserID(long userId);

    List<Tweet> getTweetsByUserIDBetweenDates(Date start, Date end, long userId);

    void removeLikesByUserID(long userID);
}
