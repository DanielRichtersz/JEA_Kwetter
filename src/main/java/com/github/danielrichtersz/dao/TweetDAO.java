package com.github.danielrichtersz.dao;

import com.github.danielrichtersz.entity.Like;
import com.github.danielrichtersz.entity.Tweet;

import javax.management.InstanceAlreadyExistsException;
import java.util.Date;
import java.util.List;

public interface TweetDAO extends BaseDAO<Tweet> {
    Like addLikeToTweet(long tweetId, long userId) throws InstanceAlreadyExistsException;
    void removeLikeFromTweet(long tweetId, long userId);

    long getNewTweetID();

    List<Tweet> getTweetsByUserID(long userId);

    List<Tweet> getTweetsByUserIDBetweenDates(Date start, Date end, long userId);

    void removeLikesByUserID(long userID);
}
