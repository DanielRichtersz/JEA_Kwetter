package com.github.danielrichtersz.ejb;

import com.github.danielrichtersz.entity.Like;
import com.github.danielrichtersz.entity.Tweet;

import javax.management.InstanceAlreadyExistsException;
import javax.ws.rs.PathParam;
import java.text.ParseException;
import java.util.Date;
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
