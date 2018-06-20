package ejb;

import entity.Like;
import entity.Tweet;

import javax.management.InstanceAlreadyExistsException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.nio.file.AccessDeniedException;
import java.text.ParseException;
import java.util.List;

public interface TweetBeanRemote {

    Tweet createTweet(String token, String userId,
                      String message) throws ParseException, AccessDeniedException;

    Tweet getTweetByTweetID(long tweetId);

    List<Tweet> getTweetsByUserID(long userId);

    List<Tweet> getTweetsByUserIDBetweenDates(
            String startdate,
            String enddate,
            long userId);

    void removeTweet(String token, String userId,
                     long tweetId) throws AccessDeniedException;

    Like addLikeToTweet(String token, String userId,
                        long tweetId) throws InstanceAlreadyExistsException, AccessDeniedException;

    void removeLikeFromTweet(String token, String userId,
                             long tweetId) throws AccessDeniedException;
}
