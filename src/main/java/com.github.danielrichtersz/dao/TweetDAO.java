package com.github.danielrichtersz.dao;

import com.github.danielrichtersz.entity.Tweet;

import javax.management.InstanceAlreadyExistsException;

public interface TweetDAO extends BaseDAO<Tweet> {
    void addLikeToTweet(long tweetId, long userId) throws InstanceAlreadyExistsException;
    void removeLikeFromTweet(long tweetId, long userId);
}
